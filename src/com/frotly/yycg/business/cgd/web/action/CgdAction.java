package com.frotly.yycg.business.cgd.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.pojo.PageParameter;
import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.base.web.action.result.DataGridResultInfo;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.base.web.action.result.SubmitResultInfo;
import com.frotly.yycg.business.cgd.entity.Yycgd;
import com.frotly.yycg.business.cgd.entity.Yycgdmx;
import com.frotly.yycg.business.cgd.pojo.YycgdCustom;
import com.frotly.yycg.business.cgd.pojo.YycgdQueryVo;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.ypml.entity.Ypxx;

/**
 * 采购单action
 * @author Frotly
 *
 */
@Controller
@Scope("prototype")
public class CgdAction extends BaseAction<YycgdQueryVo>{
	
	//进入创建采购单页面
	public String add(){
		
		return "add";
	}
	//提交采购单
	public String addresult(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String useryyid = activeUser.getSysid();
		//调用service
		Integer yycgdid = serviceFacade.getCgdService().insertYycgd(useryyid, yycgdCustom);
		Map<String, Object> sysdata = new HashMap<String, Object>();
		sysdata.put("yycgdid", yycgdid);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		//将采购单id传入edit页面
		submitResultInfo.getResultInfo().setSysdata(sysdata);
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	//进入修改页面
	public String edit(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		//获取采购单信息放入值栈
		Integer yycgdid = yycgdCustom.getId();
		Yycgd yycgd = serviceFacade.getCgdService().findYycgdById(yycgdid);
		yycgdQueryVo.setYycgd(yycgd);
		return "edit";
	}
	
	//保存修改基本信息
	public String editsubmit(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Yycgd yycgd = yycgdQueryVo.getYycgd();
		serviceFacade.getCgdService().updateYycgd(yycgd);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	//进入采购单列表页面
	public String list(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		List<Dictinfo> cgdztList = serviceFacade.getSystemService().findDictinfoByTypecode("010");
		yycgdQueryVo.setCgdztList(cgdztList);
		return "list";
	}
	
	//采购单列表json结果
	public String listresult(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String useryyId = activeUser.getSysid();
		//获取分页参数
		long total = serviceFacade.getCgdService().findYycgdListCount(useryyId, yycgdQueryVo);
		int page = yycgdQueryVo.getPage();
		int rows = yycgdQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		List<Yycgd> yycgdList = serviceFacade.getCgdService().findYycgdList(useryyId, yycgdQueryVo, firstResult, rows);
		//列表json结果
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, yycgdList);
		this.setProcessResult(dataGridResultInfo);
		return "listresult";
	}
	
	//进入添加采购药品页面
	public String addcgdmx(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		List<Dictinfo> yplbList = serviceFacade.getSystemService().findDictinfoByTypecode("001");
		yycgdQueryVo.setYplbList(yplbList);
		List<Dictinfo> jyztList = serviceFacade.getSystemService().findDictinfoByTypecode("003");
		yycgdQueryVo.setJyztList(jyztList);
		List<Dictinfo> ypzlccList = serviceFacade.getSystemService().findDictinfoByTypecode("004");
		yycgdQueryVo.setYpzlccList(ypzlccList);
		return "addcgdmx";
	}
	//所有药品信息的json结果
	public String addcgdmx_result(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		//获取分页参数
		long total = serviceFacade.getCgdService().findYpxxListCount(yycgdQueryVo);
		int page = yycgdQueryVo.getPage();
		int rows = yycgdQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		List<Ypxx> ypxxList = serviceFacade.getCgdService().findYpxxList(yycgdQueryVo, firstResult, rows);
		//列表json结果
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, ypxxList);
		this.setProcessResult(dataGridResultInfo);
		
		return "processResult";
	}
	
	//提交采购单明细信息
	public String addcgdmx_submit(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Integer yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		String ypxxids = yycgdQueryVo.getYpxxids();
		String[] ypxxids_split = ypxxids.split(",");
		List<String> ypxxidList = new ArrayList<String>();
		for (String ypxxid : ypxxids_split) {
			ypxxidList.add(ypxxid);
		}
		//添加采购单明细，即选择的药品信息
		serviceFacade.getCgdService().insertYycgdmx(yycgdid, ypxxidList);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	//采购单明细json结果
	public String yycgdmx_result(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Integer yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		//获取分页参数
		long total = serviceFacade.getCgdService().findYycgdmxListCount(yycgdid, yycgdQueryVo);
		int page = yycgdQueryVo.getPage();
		int rows = yycgdQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		List<Yycgdmx> yycgdmxList = serviceFacade.getCgdService().findYycgdmxList(yycgdid, yycgdQueryVo, firstResult, rows);
		//列表json结果
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, yycgdmxList);
		this.setProcessResult(dataGridResultInfo);
		return "yycgdmx_result";
	}
	
	//更新采购单明细信息，如采购量
	public String savecgdmx(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Integer yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		List<Yycgdmx> yycgdmxList = yycgdQueryVo.getYycgdmxList();
		String indexs = yycgdQueryVo.getIndexs();
		String[] indexs_split = indexs.split(",");
		List<Yycgdmx> yycgdmxs = new ArrayList<Yycgdmx>();
		for (String index : indexs_split) {
			yycgdmxs.add(yycgdmxList.get(Integer.parseInt(index)));
		}
		serviceFacade.getCgdService().updateYycgdmx(yycgdid, yycgdmxs);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	//提交采购单
	public String submit(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Integer yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		String taskId = yycgdQueryVo.getTaskId();
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String assignee = activeUser.getId();
		serviceFacade.getCgdService().saveYycgdSubmitState(yycgdid, taskId, assignee);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	//进入采购单审核页面
	public String checklist(){
		
		return "checklist";
	}
	//采购单审核列表页面
	
	public String checklist_result(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String userjdid = activeUser.getSysid();
		//获取分页参数
		long total = serviceFacade.getCgdService().findCheckYycgdListCount(userjdid, yycgdQueryVo);
		int page = yycgdQueryVo.getPage();
		int rows = yycgdQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		List<Yycgd> yycgdList = serviceFacade.getCgdService().findCheckYycgdList(userjdid, yycgdQueryVo, firstResult, rows);
		//列表json结果
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, yycgdList);
		this.setProcessResult(dataGridResultInfo);
		return "checklist_result";
	}
	//进入审核页面
	public String check(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Integer yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		Yycgd yycgd = serviceFacade.getCgdService().findYycgdById(yycgdid);
		yycgdQueryVo.setYycgd(yycgd);
		return "check";
	}
	//提交审核结果
	public String checksubmit(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		Integer yycgdid = yycgdCustom.getId();
		String taskId = yycgdQueryVo.getTaskId();
		String checkResult = yycgdCustom.getCheckResult();
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String assignee = activeUser.getId();
		serviceFacade.getCgdService().saveYycgdCheckState(taskId, assignee, yycgdid, checkResult, yycgdCustom);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	//进入受理采购单列表页面
	public String disposelist(){
		
		return "disposelist";
	}
	//受理采购单列表
	public String disposelist_result(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String usergysid = activeUser.getSysid();
		//获取分页参数
		long total = serviceFacade.getCgdService().findDisposeYycgdListCount(usergysid, yycgdQueryVo);
		int page = yycgdQueryVo.getPage();
		int rows = yycgdQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		List<Yycgd> yycgdList = serviceFacade.getCgdService().findDisposeYycgdList(usergysid, yycgdQueryVo, firstResult, rows);
		//列表json结果
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, yycgdList);
		this.setProcessResult(dataGridResultInfo);
		return "disposelist_result";
	}
	//进入受理页面
	public String dispose(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		Integer yycgdid = yycgdCustom.getId();
		Yycgd yycgd = serviceFacade.getCgdService().findYycgdById(yycgdid);
		yycgdQueryVo.setYycgd(yycgd);
		
		//设置受理状态
		List<Dictinfo> cgztList = serviceFacade.getSystemService().findDictinfoByTypecode("011");
		yycgdQueryVo.setCgztList(cgztList);
		return "dispose";
	}
	
	//保存药品受理状态完成
	public String sendsubmit(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Integer yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		List<Yycgdmx> yycgdmxList = yycgdQueryVo.getYycgdmxList();
		String indexs = yycgdQueryVo.getIndexs();
		String[] indexs_split = indexs.split(",");
		List<Yycgdmx> yycgdmxs = new ArrayList<Yycgdmx>();
		for (String index : indexs_split) {
			yycgdmxs.add(yycgdmxList.get(Integer.parseInt(index)));
		}
		serviceFacade.getCgdService().saveDisposeState(yycgdid, yycgdmxs);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
	//受理完成
	public String disposesubmit(){
		//获取页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		Integer yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		String taskId = yycgdQueryVo.getTaskId();
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String assignee = activeUser.getId();
		serviceFacade.getCgdService().saveFinishDisposeState(yycgdid, taskId, assignee);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
}










