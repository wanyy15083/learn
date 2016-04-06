package com.frotly.yycg.business.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.pojo.PageParameter;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.SysPermission;
import com.frotly.yycg.business.system.entity.SysRole;
import com.frotly.yycg.business.system.entity.SysUser;
import com.frotly.yycg.business.system.entity.Usergys;
import com.frotly.yycg.business.system.entity.Userjd;
import com.frotly.yycg.business.system.entity.Useryy;
import com.frotly.yycg.business.system.pojo.SysUserCustom;
import com.frotly.yycg.business.system.pojo.SysUserQueryVo;
import com.frotly.yycg.business.system.service.UserService;
import com.frotly.yycg.util.MD5;
import com.frotly.yycg.util.MyUtil;

@Service
public class UserServiceImpl extends BaseService implements UserService {
	@Resource(name="userDao")
	private BaseDao<SysUser> userDao;
	@Resource(name="sysRoleDao")
	private BaseDao<SysRole> sysRoleDao;
	@Resource(name="userjdDao")
	private BaseDao<Userjd> userjdDao;
	@Resource(name="useryyDao")
	private BaseDao<Useryy> useryyDao;
	@Resource(name="usergysDao")
	private BaseDao<Usergys> usergysDao;

	@Override
	public SysUser findSysUserById(String id) {
		return userDao.findById(id);
	}

	@Override
	public SysUser findSysUserByUsercode(String usercode) {
		if (StringUtils.isEmpty(usercode)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 218, null));
		}
		DetachedCriteria detachedCriteria = userDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("usercode", usercode));
		List<SysUser> list = userDao.findListByCtriteria(detachedCriteria);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertSysUser(SysUserCustom sysUserCustom) {
		if (sysUserCustom == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 901, null));
		}
		SysUser sysUser1 = this.findSysUserByUsercode(sysUserCustom.getUsercode());
		if (sysUser1 != null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 208, null));
		}
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(sysUserCustom, sysUser);
		String md5Pwd = new MD5().getMD5ofStr(sysUser.getPwd());
		sysUser.setPwd(md5Pwd);
		Dictinfo dictinfoByGroupid = serviceFacade.getSystemService().findDictinfoById(sysUserCustom.getGroupid());
		sysUser.setDictinfoByGroupid(dictinfoByGroupid);
		String dictcode = dictinfoByGroupid.getDictcode();
		if ("1".equals(dictcode) || "2".equals(dictcode)) {
			Userjd userjd = this.findUserjdByMc(sysUserCustom.getSysmc());
			if (userjd == null) {
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			}
			sysUser.setUserjd(userjd);
		}
		if ("3".equals(dictcode)) {
			Useryy useryy = this.findUseryyByMc(sysUserCustom.getSysmc());
			if (useryy == null) {
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			}
			sysUser.setUseryy(useryy);
		}
		if ("4".equals(dictcode)) {
			Usergys usergys = this.findUsergysByMc(sysUserCustom.getSysmc());
			if (usergys == null) {
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			}
			sysUser.setUsergys(usergys);
		}
		Dictinfo dictinfoByUserstate = serviceFacade.getSystemService().findDictinfoById(sysUserCustom.getUserstate());
		sysUser.setDictinfoByUserstate(dictinfoByUserstate);
		userDao.insert(sysUser);
		//添加用户时设置角色
		String roleId = sysUser.getDictinfoByGroupid().getId();
		SysRole sysRole = sysRoleDao.findById(roleId);
		sysUser.getSysRoles().add(sysRole);
		
		//整合activiti，添加activiti表中用户、组及其关系
		IdentityService identityService = serviceFacade.getProcessEngine().getIdentityService();
		//添加用户
		UserEntity user = new UserEntity();
		user.setId(sysUser.getId());
		user.setFirstName(sysUser.getUsername());
		user.setLastName(sysUser.getUsername());
		user.setPassword(sysUser.getPwd());
		identityService.saveUser(user);
		
		//添加组(应该在添加角色时添加组)
		//角色不存在再添加
		if (identityService.createGroupQuery().groupId(roleId).singleResult() == null) {
			GroupEntity group = new GroupEntity();
			group.setId(roleId);
			group.setName(sysRole.getName());
			identityService.saveGroup(group);
		}
		
		//添加关系,先删除再添加
		identityService.deleteMembership(sysUser.getId(), roleId);
		identityService.createMembership(sysUser.getId(), roleId);
	}

	@Override
	public void updateSysUser(SysUserCustom sysUserCustom) {

	}

	@Override
	public void deleteysUser(String userId) {
		if (StringUtils.isEmpty(userId)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 901, null));
		}
		SysUser sysUser = this.findSysUserById(userId);
		sysUser.getSysRoles().clear();
		userDao.delete(sysUser);
	}

	@Override
	public List<SysUser> findSysUserList(SysUserQueryVo sysUserQueryVo,
			int firstResult, int maxResults) {
		DetachedCriteria detachedCriteria = userDao.createDetachedCriteria();
		this.findUserListByCondition(sysUserQueryVo, detachedCriteria);
		return userDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public Long findSysUserListCount(SysUserQueryVo sysUserQueryVo) {
		DetachedCriteria detachedCriteria = userDao.createDetachedCriteria();
		return userDao.findListCount(detachedCriteria);
	}

	@Override
	public Userjd findUserjdByMc(String mc) {
		if (mc == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 216, null));
		}
		DetachedCriteria detachedCriteria = userjdDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("mc", mc));
		return userjdDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public Useryy findUseryyByMc(String mc) {
		if (mc == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 216, null));
		}
		DetachedCriteria detachedCriteria = useryyDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("mc", mc));
		return useryyDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public Usergys findUsergysByMc(String mc) {
		if (mc == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 216, null));
		}
		DetachedCriteria detachedCriteria = usergysDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq("mc", mc));
		return usergysDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public ActiveUser checkUser(String usercode, String password) {
		if (StringUtils.isEmpty(usercode) || StringUtils.isEmpty(password)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 901, null));
		}
		SysUser sysUser = this.findSysUserByUsercode(usercode);
		if (sysUser == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 101, null));
		}
		String md5ofPwd = new MD5().getMD5ofStr(password);
		if (!md5ofPwd.equalsIgnoreCase(sysUser.getPwd())) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 114, null));
		}
		
		ActiveUser activeUser = new ActiveUser();
		activeUser.setId(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setGroupid(sysUser.getDictinfoByGroupid().getId());
		activeUser.setGroupname(sysUser.getDictinfoByGroupid().getInfo());
		String groupid = activeUser.getGroupid();
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoById(groupid);
		String dictcode = dictinfo.getDictcode();
		String sysid = null;
		String sysmc = null;
		if ("1".equals(dictcode) || "2".equals(dictcode)) {
			sysid = sysUser.getUserjd().getId();
			sysmc = sysUser.getUserjd().getMc();
		} else if("3".equals(dictcode)) {
			sysid = sysUser.getUseryy().getId();
			sysmc = sysUser.getUseryy().getMc();
		} else if("4".equals(dictcode)) {
			sysid = sysUser.getUsergys().getId();
			sysmc = sysUser.getUsergys().getMc();
		}
		activeUser.setSysid(sysid);
		activeUser.setSysmc(sysmc);
		
		List<SysPermission> sysPermissionList = serviceFacade.getAuthService().findPermissionListByUserId(sysUser.getId());
		activeUser.setSysPermissionList(sysPermissionList);
		return activeUser;
	}

	@Override
	public ActiveUser createActiveUser(String usercode) {
		if (StringUtils.isEmpty(usercode)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 218, null));
		}
		SysUser sysUser = this.findSysUserByUsercode(usercode);
		ActiveUser activeUser = new ActiveUser();
		activeUser.setId(sysUser.getId());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setGroupid(sysUser.getDictinfoByGroupid().getId());
		activeUser.setGroupname(sysUser.getDictinfoByGroupid().getInfo());
		List<SysPermission> permissionListByUserId = serviceFacade.getAuthService().findPermissionListByUserId(sysUser.getId());
		activeUser.setSysPermissionList(permissionListByUserId);
		String sysid = null;
		String sysmc = null;
		String dictcode = sysUser.getDictinfoByGroupid().getDictcode();
		if ("1".equals(dictcode) || "2".equals(dictcode)) {
			Userjd userjd = sysUser.getUserjd();
			sysid = userjd.getId();
			sysmc = userjd.getMc();
		}
		if ("3".equals(dictcode)) {
			Useryy useryy = sysUser.getUseryy();
			sysid = useryy.getId();
			sysmc = useryy.getMc();
		}
		if ("4".equals(dictcode)) {
			Usergys usergys = sysUser.getUsergys();
			sysid = usergys.getId();
			sysmc = usergys.getMc();
		}
		activeUser.setSysid(sysid);
		activeUser.setSysmc(sysmc);
		return activeUser;
	}

	
	public void findUserListByCondition(SysUserQueryVo sysUserQueryVo,DetachedCriteria detachedCriteria){
		if (sysUserQueryVo != null) {
			SysUserCustom sysUserCustom = sysUserQueryVo.getSysUserCustom();
			if (sysUserCustom != null) {
				if (StringUtils.isNotEmpty(sysUserCustom.getUsercode())) {
					detachedCriteria.add(Restrictions.eq("usercode", sysUserCustom.getUsercode()));
				}
				if (StringUtils.isNotEmpty(sysUserCustom.getUsername())) {
					detachedCriteria.add(Restrictions.like("username", "%"+sysUserCustom.getUsername()+"%"));
				}
				if (StringUtils.isNotEmpty(sysUserCustom.getGroupid())) {
					detachedCriteria.add(Restrictions.eq("dictinfoByGroupid.id", sysUserCustom.getGroupid()));
				}
			}
		}
	}

	@Override
	public List<Useryy> findUseryyList(String dq) {
		DetachedCriteria detachedCriteria = useryyDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.like("sysArea.id", dq+"%"));
		return useryyDao.findListByCtriteria(detachedCriteria);
	}
}
