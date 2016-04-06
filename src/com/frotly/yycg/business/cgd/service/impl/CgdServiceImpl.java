package com.frotly.yycg.business.cgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.business.analyze.entity.Yybusiness;
import com.frotly.yycg.business.cgd.entity.Yycgd;
import com.frotly.yycg.business.cgd.entity.Yycgdmx;
import com.frotly.yycg.business.cgd.pojo.YycgdCustom;
import com.frotly.yycg.business.cgd.pojo.YycgdQueryVo;
import com.frotly.yycg.business.cgd.service.CgdService;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.SysArea;
import com.frotly.yycg.business.system.entity.Usergys;
import com.frotly.yycg.business.system.entity.Usergysarea;
import com.frotly.yycg.business.system.entity.Userjd;
import com.frotly.yycg.business.system.entity.Useryy;
import com.frotly.yycg.business.ypml.entity.Ypxx;
import com.frotly.yycg.business.ypml.pojo.YpxxCustom;
import com.frotly.yycg.util.MyUtil;

/**
 * 采购单业务service
 * @author Frotly
 *
 */
@Service
public class CgdServiceImpl extends BaseService implements CgdService{

	@Resource(name="yycgdDao")
	private BaseDao<Yycgd> yycgdDao;
	@Resource(name="usergysareaDao")
	private BaseDao<Usergysarea> usergysareaDao;
	@Resource(name="ypxxDao")
	private BaseDao<Ypxx> ypxxDao;
	@Resource(name="yycgdmxDao")
	private BaseDao<Yycgdmx> yycgdmxDao;
	@Resource(name="userjdDao")
	private BaseDao<Userjd> userjdDao;
	@Resource(name="useryyDao")
	private BaseDao<Useryy> useryyDao;
	@Resource(name="businessDao")
	private BaseDao<Yybusiness> businessDao;
	
	@Override
	public Integer insertYycgd(String useryyid, YycgdCustom yycgdCustom) {
		Useryy useryy = useryyDao.findById(useryyid);
		if (useryy == null) {
			
		}
		Yycgd yycgd = new Yycgd();
		BeanUtils.copyProperties(yycgdCustom, yycgd);
		//设置医院
		yycgd.setUseryy(useryy);
		//设置建单时间
		yycgd.setCjtime(MyUtil.getNowDate());
		//设置采购单状态
		Dictinfo dictinfoByZt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("010", "1");
		yycgd.setDictinfoByZt(dictinfoByZt);
		//设置供应商
		SysArea sysArea = useryy.getSysArea();
		String parentid = sysArea.getParentid();
		Usergysarea usergysarea = usergysareaDao.findById(parentid);
		Usergys usergys = usergysarea.getUsergys();
		yycgd.setUsergys(usergys);
		//插入操作
		yycgdDao.insert(yycgd);

		//整合activiti，启动一个流程实例
		//设置流程发起人
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		IdentityService identityService = serviceFacade.getProcessEngine().getIdentityService();
		identityService.setAuthenticatedUserId(activeUser.getId());
		
		//添加采购单时启动一个流程实例，与业务流程关联
		RuntimeService runtimeService = serviceFacade.getProcessEngine().getRuntimeService();
		//通过流程定义key和bussinessKey启动流程实例
		//ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(Config.FLOW_KEY_PURCHASING, yycgd.getId()+"");
		//在业务系统中采购单表中记录流程实例id
		
		//设置流程变量
		Map<String, Object> variables = new HashMap<String,Object>();
		//设置医院上级区域
		String userjd_areaid = yycgd.getUseryy().getSysArea().getParentid();
		variables.put("userjd_areaid", userjd_areaid);
		//设置供货商
		String usergysid = yycgd.getUsergys().getId();
		variables.put("usergysid", usergysid);
		
		//启动流程实例
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(Config.FLOW_KEY_PURCHASING, yycgd.getId()+"", variables);
		yycgd.setProcessinstanceid(processInstance.getId());
		//返回采购单id
		return yycgd.getId();
	}

	@Override
	public Yycgd findYycgdById(Integer yycgdid) {
		return yycgdDao.findById(yycgdid);
	}

	@Override
	public List<Yycgd> findYycgdList(String useryyId,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults) {
		DetachedCriteria detachedCriteria = yycgdDao.createDetachedCriteria();
		this.findYycgdListByCondition(useryyId, yycgdQueryVo, detachedCriteria);
		return yycgdDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	private void findYycgdListByCondition(String useryyId,
			YycgdQueryVo yycgdQueryVo, DetachedCriteria detachedCriteria) {
			//查询条件
		detachedCriteria.add(Restrictions.eq("useryy.id", useryyId));
		if (yycgdQueryVo != null) {
			YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
			if (yycgdCustom != null) {
				if (yycgdCustom.getYycgdbm() != null) {
					detachedCriteria.add(Restrictions.eq("id", yycgdCustom.getYycgdbm()));
				}
				if (StringUtils.isNotEmpty(yycgdCustom.getMc())) {
					detachedCriteria.add(Restrictions.like("mc", "%"+yycgdCustom.getMc()+"%"));
				}
				if (StringUtils.isNotEmpty(yycgdCustom.getCgdzt())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByZt.id", yycgdCustom.getCgdzt()));
				}
			}
		}
	}

	@Override
	public Long findYycgdListCount(String useryyId, YycgdQueryVo yycgdQueryVo) {
		DetachedCriteria detachedCriteria = yycgdDao.createDetachedCriteria();
		this.findYycgdListByCondition(useryyId, yycgdQueryVo, detachedCriteria);
		return yycgdDao.findListCount(detachedCriteria);
	}

	@Override
	public List<Ypxx> findYpxxList(YycgdQueryVo yycgdQueryVo, int firstResult,
			int maxResults) {
		DetachedCriteria detachedCriteria = ypxxDao.createDetachedCriteria();
		this.findYpxxListCondition(detachedCriteria, yycgdQueryVo);
		return ypxxDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	private void findYpxxListCondition(DetachedCriteria detachedCriteria,YycgdQueryVo yycgdQueryVo){
		if (yycgdQueryVo != null) {
			YpxxCustom ypxxCustom = yycgdQueryVo.getYpxxCustom();
			if (ypxxCustom != null) {
				if (StringUtils.isNotEmpty(ypxxCustom.getPinyin())) {
					detachedCriteria.add(Restrictions.like("pingyin", "%"+ypxxCustom.getPinyin()+"%"));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getJx())) {
					detachedCriteria.add(Restrictions.eq("jx", ypxxCustom.getJx()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getGg())) {
					detachedCriteria.add(Restrictions.eq("gg", ypxxCustom.getGg()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getZhxs())) {
					detachedCriteria.add(Restrictions.eq("zhxs", ypxxCustom.getZhxs()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getBm())) {
					detachedCriteria.add(Restrictions.eq("bm", ypxxCustom.getBm()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getScqymc())) {
					detachedCriteria.add(Restrictions.like("scqymc", "%"+ypxxCustom.getScqymc()+"%"));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getSpmc())) {
					detachedCriteria.add(Restrictions.like("spmc", "%"+ypxxCustom.getSpmc()+"%"));
				}
				if (ypxxCustom.getZbjg() != null) {
					detachedCriteria.add(Restrictions.between("zbjg", ypxxCustom.getZbjglower(), ypxxCustom.getZbjgupper()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getLb())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByLb.id", ypxxCustom.getLb()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getJyzt())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByJyzt.id", ypxxCustom.getJyzt()));
				}
				if (StringUtils.isNotEmpty(ypxxCustom.getZlcc())) {
					detachedCriteria.add(Restrictions.eq("zlcc", ypxxCustom.getZlcc()));
				}
			}
		}
	}
	
	@Override
	public Long findYpxxListCount(YycgdQueryVo yycgdQueryVo) {
		DetachedCriteria detachedCriteria = ypxxDao.createDetachedCriteria();
		this.findYpxxListCondition(detachedCriteria, yycgdQueryVo);
		return ypxxDao.findListCount(detachedCriteria);
	}

	@Override
	public void updateYycgd(Yycgd yycgd) {
		if (yycgd == null) {
			//更新失败
		}
		yycgdDao.update(yycgd);
	}

	@Override
	public void insertYycgdmx(Integer yycgdid, List<String> ypxxids) {
		//获取采购单
		Yycgd yycgd = this.findYycgdById(yycgdid);
		if (yycgd == null) {
			//无法获取采购单信息
		}
		//根据药品信息id获取药品明细，添加到采购单明细中
		if (ypxxids.size() < 0 || ypxxids == null) {
			//请选择药品
		}
		
		for (String ypxxid : ypxxids) {
			Ypxx ypxx = ypxxDao.findById(ypxxid);
			Yycgdmx yycgdmx = new Yycgdmx();
			yycgdmx.setYycgd(yycgd);
			yycgdmx.setYpxx(ypxx);
			Float zbjg = ypxx.getZbjg();
			yycgdmx.setZbjg(zbjg);
			yycgdmx.setJyjg(zbjg);
			yycgdmx.setCgl(0);
			yycgdmx.setCgje(0f);
			Dictinfo dictinfoByCgzt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("011", "1");
			yycgdmx.setDictinfoByCgzt(dictinfoByCgzt);
			yycgd.getYycgdmxes().add(yycgdmx);
		}
	}

	@Override
	public List<Yycgdmx> findYycgdmxList(Integer yycgdid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults) {
		DetachedCriteria detachedCriteria = yycgdmxDao.createDetachedCriteria();
		this.findYycgdmxListByCondition(yycgdid,yycgdQueryVo,detachedCriteria);
		return yycgdmxDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	private void findYycgdmxListByCondition(Integer yycgdid,
			YycgdQueryVo yycgdQueryVo,DetachedCriteria detachedCriteria) {
		detachedCriteria.add(Restrictions.eq("yycgd.id", yycgdid));
		if (yycgdQueryVo != null) {
			YpxxCustom ypxxCustom = yycgdQueryVo.getYpxxCustom();
			if (ypxxCustom != null) {
				if (StringUtils.isNotEmpty(ypxxCustom.getBm())) {
					//定义别名
					//第一个参数：关联实体类的名称，第二个：别名，第三个：joinType，默认为0，生成的sql为内连接方式，为1生成的sql为左外连接
					detachedCriteria.createAlias("ypxx", "ypxx");
					detachedCriteria.add(Restrictions.eq("ypxx.bm", ypxxCustom.getBm()));
				}
			}
		}
	}

	@Override
	public Long findYycgdmxListCount(Integer yycgdid, YycgdQueryVo yycgdQueryVo) {
		DetachedCriteria detachedCriteria = yycgdmxDao.createDetachedCriteria();
		this.findYycgdmxListByCondition(yycgdid,yycgdQueryVo,detachedCriteria);
		return yycgdmxDao.findListCount(detachedCriteria);
	}

	@Override
	public Yycgdmx findYycgdmxByYycgdidAndYpxxid(Integer yycgdid, String ypxxid) {
		DetachedCriteria detachedCriteria = yycgdmxDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("yycgd.id", yycgdid));
		detachedCriteria.add(Restrictions.eq("ypxx.id", ypxxid));
		return yycgdmxDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public void updateYycgdmx(Integer yycgdid, List<Yycgdmx> yycgdmxs) {
		Yycgd yycgd = this.findYycgdById(yycgdid);
		if (yycgd == null) {
			//无法获取采购单id
		}
		if (yycgdmxs == null || yycgdmxs.size() <= 0) {
			//要更新的采购单明细不能为空
		}
		//遍历要更新的药品明细信息
		for (Yycgdmx yycgdmx : yycgdmxs) {
			//更新采购量、采购金额等信息
			String ypxxid = yycgdmx.getYpxx().getId();
			Integer cgl = yycgdmx.getCgl();
			Yycgdmx yycgdmx_update = this.findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxid);
			yycgdmx_update.setCgl(cgl);
			yycgdmx_update.setCgje(cgl * yycgdmx_update.getJyjg());
		}
	}

	@Override
	public void saveYycgdSubmitState(Integer yycgdid, String taskId,
			String assignee) {
		Yycgd yycgd = yycgdDao.findById(yycgdid);
		if (yycgd == null) {
			//要提交的采购单不能为空
		}
		//判断采购单的状态为“未提交”和“审核不通过”
		String dictcode = yycgd.getDictinfoByZt().getDictcode();
		if (!"1".equals(dictcode) && !"4".equals(dictcode)) {
			//采购单不可提交,请确认采购单 的状态
		}
		//保存采购单的状态
		Dictinfo dictinfoByZt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("010", "2");
		yycgd.setDictinfoByZt(dictinfoByZt);
		//设置提交时间
		yycgd.setTjtime(MyUtil.getNowDate());
		
		//整合activiti,提交采购单，任务流程执行一个节点
		//获得taskService
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		//创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		//绑定查询条件
		Task task = taskQuery.taskAssignee(assignee).taskId(taskId).singleResult();
		//任务完成
		if (task != null) {
			taskService.complete(taskId);
		}
		
	}

	@Override
	public List<Yycgd> findCheckYycgdList(String userjdid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults) {
		DetachedCriteria detachedCriteria = yycgdDao.createDetachedCriteria();
		this.findCheckYycgdListByCondition(userjdid, yycgdQueryVo,detachedCriteria);
		return yycgdDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	//查询审核采购单的条件
	private void findCheckYycgdListByCondition(String userjdid,
			YycgdQueryVo yycgdQueryVo, DetachedCriteria detachedCriteria) {
		//根据监督单位的id查询到审核的地区，查询到地区下所有卫生院下的订单
		Userjd userjd = userjdDao.findById(userjdid);
		String dq = userjd.getDq();
		List<Useryy> useryyList = serviceFacade.getUserService().findUseryyList(dq);
		List<String> useryyidList = new ArrayList<String>();
		for (Useryy useryy : useryyList) {
			useryyidList.add(useryy.getId());
		}
		detachedCriteria.add(Restrictions.in("useryy.id", useryyidList));
		//查询到已提交未审核的采购单
		Dictinfo dictinfoByZt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("010", "2");
		detachedCriteria.add(Restrictions.eq("dictinfoByZt.id", dictinfoByZt.getId()));

		if (yycgdQueryVo != null) {
			YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
			if (yycgdCustom != null) {
				if (yycgdCustom.getId() != null) {
					detachedCriteria.add(Restrictions.eq("id", yycgdCustom.getId()));
				}
				if (StringUtils.isNotEmpty(yycgdCustom.getMc())) {
					detachedCriteria.add(Restrictions.like("mc", "%"+yycgdCustom.getMc()+"%"));
				}
				if (StringUtils.isNotEmpty(yycgdCustom.getCgdzt())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByZt.id", yycgdCustom.getCgdzt()));
				}
			}
		}
	}

	@Override
	public Long findCheckYycgdListCount(String userjdid,
			YycgdQueryVo yycgdQueryVo) {
		DetachedCriteria detachedCriteria = yycgdDao.createDetachedCriteria();
		this.findCheckYycgdListByCondition(userjdid, yycgdQueryVo,detachedCriteria);
		return yycgdDao.findListCount(detachedCriteria);
	}

	@Override
	public void saveYycgdCheckState(String taskId, String assignee,
			Integer yycgdid, String checkResult, YycgdCustom yycgdCustom) {
		Yycgd yycgd = yycgdDao.findById(yycgdid);
		if (yycgd == null) {
			//要审核的采购单不存在
		}
		//审核结果的合法性
		if (checkResult == null || (!"3".equals(checkResult) && !"4".equals(checkResult))) {
			//当前采购单的审核结果不合法
		}
		//保存审核结果
		Dictinfo dictinfoByZt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("010", checkResult);
		yycgd.setDictinfoByZt(dictinfoByZt);
		//保存审核时间
		yycgd.setShtime(MyUtil.getNowDate());
		//保存审核意见
		yycgd.setShyj(yycgdCustom.getShyj());
		
		//整合activiti，完成审核任务节点
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		//获取查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		//绑定查询条件,获取任务对象
		Task task = taskQuery.taskAssignee(assignee).taskId(taskId).singleResult();
		if (task == null) {
			//该用户没有完成该任务的权限
		}
		//定义流程变量
		Map<String , Object> variables = new HashMap<String, Object>();
		YycgdCustom yycgdCustom_var = new YycgdCustom();
		if ("3".equals(checkResult)) {
			yycgdCustom_var.setCheckResult("3");
		}else if ("4".equals(checkResult)) {
			yycgdCustom_var.setCheckResult("4");
		}
		yycgdCustom_var.setCheckResult(checkResult);//???完整性
		//设置审核结果
		variables.put("yycgdCustom", yycgdCustom_var);
		//完成任务,设置流程变量
		taskService.complete(taskId, variables);
		
		//数据聚合，审核成功后，向yybusiness表中插入采购单明细
		if ("3".equals(checkResult)) {
			Set yycgdmxes = yycgd.getYycgdmxes();
			Iterator iterator = yycgdmxes.iterator();
			while (iterator.hasNext()) {
				Yycgdmx yycgdmx = (Yycgdmx) iterator.next();
				Yybusiness yybusiness = new Yybusiness();
				yybusiness.setYycgd(yycgd);
				yybusiness.setUseryy(yycgd.getUseryy());
				yybusiness.setUsergys(yycgd.getUsergys());
				yybusiness.setYpxx(yycgdmx.getYpxx());
				yybusiness.setZbjg(yycgdmx.getZbjg());
				yybusiness.setJyjg(yycgdmx.getJyjg());
				yybusiness.setCgje(yycgdmx.getCgje());
				yybusiness.setCgl(yycgdmx.getCgl());
				yybusiness.setDictinfo(yycgdmx.getDictinfoByCgzt());
				yybusiness.setTjtime(yycgd.getTjtime());
				businessDao.insert(yybusiness);
			}
		}
	}

	@Override
	public List<Yycgd> findDisposeYycgdList(String usergysid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults) {
		DetachedCriteria detachedCriteria = yycgdDao.createDetachedCriteria();
		this.findDisposeYycgdListByCondition(usergysid,yycgdQueryVo,detachedCriteria);
		return yycgdDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	private void findDisposeYycgdListByCondition(String usergysid,
			YycgdQueryVo yycgdQueryVo,DetachedCriteria detachedCriteria) {
		//查询供应商供应区域的采购单
		detachedCriteria.add(Restrictions.eq("usergys.id", usergysid));
		//查询审核通过的采购单
		Dictinfo dictinfoByZt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("010", "3");
		detachedCriteria.add(Restrictions.eq("dictinfoByZt.id", dictinfoByZt.getId()));
		if (yycgdQueryVo != null) {
			YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
			if (yycgdCustom != null) {
				if (yycgdCustom.getId() != null) {
					detachedCriteria.add(Restrictions.eq("id", yycgdCustom.getId()));
				}
				if (StringUtils.isNotEmpty(yycgdCustom.getMc())) {
					detachedCriteria.add(Restrictions.like("mc", "%"+yycgdCustom.getMc()+"%"));
				}
				if (StringUtils.isNotEmpty(yycgdCustom.getCgdzt())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByZt.id", yycgdCustom.getCgdzt()));
				}
			}
		}
	}

	@Override
	public Long findDisposeYycgdListCount(String usergysid,
			YycgdQueryVo yycgdQueryVo) {
		DetachedCriteria detachedCriteria = yycgdDao.createDetachedCriteria();
		this.findDisposeYycgdListByCondition(usergysid,yycgdQueryVo,detachedCriteria);
		return yycgdDao.findListCount(detachedCriteria);
	}

	@Override
	public void saveDisposeState(Integer yycgdid, List<Yycgdmx> yycgdmxs) {
		Yycgd yycgd = this.findYycgdById(yycgdid);
		if (yycgd == null) {
			//无法获取采购单id
		}
		for (Yycgdmx yycgdmx : yycgdmxs) {
			String ypxxid = yycgdmx.getYpxx().getId();
			//查询一条采购单明细记录
			Yycgdmx yycgdmx_update = this.findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxid);
			yycgdmx_update.setDictinfoByCgzt(yycgdmx.getDictinfoByCgzt());
			yycgdmxDao.update(yycgdmx_update);
			
			//聚合数据
			Yybusiness yybusiness = serviceFacade.getBusinessService().findYybusinessByYycgdidAndYpxxid(yycgdid, ypxxid);
			yybusiness.setDictinfo(yycgdmx.getDictinfoByCgzt());
			businessDao.update(yybusiness);
		}
	}

	@Override
	public void saveFinishDisposeState(Integer yycgdid, String taskId,
			String assignee) {
		Yycgd yycgd = this.findYycgdById(yycgdid);
		if (yycgd == null) {
			//无法获取采购单id
		}
		//必须将采购单明细全部设置受理状态
		Set yycgdmxes = yycgd.getYycgdmxes();
		Iterator yycgdmxes_iterator = yycgdmxes.iterator();
		while (yycgdmxes_iterator.hasNext()) {
			Yycgdmx yycgdmx = (Yycgdmx) yycgdmxes_iterator.next();
			Dictinfo dictinfoByCgzt = yycgdmx.getDictinfoByCgzt();
			if (dictinfoByCgzt == null || !"011".equals(dictinfoByCgzt.getTypecode()) || (!"2".equals(dictinfoByCgzt.getDictcode()) && !"3".equals(dictinfoByCgzt.getDictcode()))) {
				//请确认所有药品已受理
			}
		}
		//更新采购单受理状态
		Dictinfo dictinfoByZt = serviceFacade.getSystemService().findDictinfoByTypecodeAndDictcode("010", "5");
		yycgd.setDictinfoByZt(dictinfoByZt);
		
		//整合activiti，完成受理任务
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		//创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		//绑定查询条件
		//获取查询结果
		Task task = taskQuery.taskAssignee(assignee).taskId(taskId).singleResult();
		//完成任务
		if (task != null) {
			taskService.complete(taskId);
		}
	}
	
	

}
