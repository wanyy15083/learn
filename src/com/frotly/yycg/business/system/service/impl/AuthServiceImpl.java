package com.frotly.yycg.business.system.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.pojo.Menu;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.business.system.entity.SysPermission;
import com.frotly.yycg.business.system.entity.SysRole;
import com.frotly.yycg.business.system.entity.SysUser;
import com.frotly.yycg.business.system.pojo.SysRoleQueryVo;
import com.frotly.yycg.business.system.service.AuthService;

@Service
public class AuthServiceImpl extends BaseService implements AuthService {

	@Resource(name="sysRoleDao")
	private BaseDao<SysRole> sysRoleDao;
	@Resource(name="sysPermissionDao")
	private BaseDao<SysPermission> sysPermissionDao;
	
	@Override
	public List<SysRole> findSysRoleList(SysRoleQueryVo sysRoleQueryVo,
			int firstResult, int maxResults) {
		DetachedCriteria detachedCriteria = sysRoleDao.createDetachedCriteria();
		return sysRoleDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public Long findSysRoleCount() {
		DetachedCriteria detachedCriteria = sysRoleDao.createDetachedCriteria();
		return sysRoleDao.findListCount(detachedCriteria);
	}

	@Override
	public List<SysPermission> findSysPermissionList() {
		DetachedCriteria detachedCriteria = sysPermissionDao.createDetachedCriteria();
		detachedCriteria.addOrder(Order.asc("showorder"));
		return sysPermissionDao.findListByCtriteria(detachedCriteria);
	}

	@Override
	public List<SysPermission> findSysPermissionByRoleId(String roleId) {
		if (StringUtils.isEmpty(roleId)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		SysRole sysRole = sysRoleDao.findById(roleId);
		Set sysPermissions = sysRole.getSysPermissions();
		List<SysPermission> sysPermissionList = new ArrayList<SysPermission>();
		sysPermissionList.addAll(sysPermissions);
		return sysPermissionList;
	}

	@Override
	public void saveRoleAuthorize(String roleId, List<String> sysPermissionids) {
		if (StringUtils.isEmpty(roleId)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		SysRole sysRole = sysRoleDao.findById(roleId);
		if (sysRole == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		sysRole.getSysPermissions().clear();
		for (String sysPermissionid : sysPermissionids) {
			SysPermission sysPermission = sysPermissionDao.findById(sysPermissionid);
			if (sysPermission != null) {
				sysRole.getSysPermissions().add(sysPermission);
			}
		}
	}

	@Override
	public List<SysPermission> findPermissionListByUserId(String userId) {
		if (StringUtils.isEmpty(userId)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		SysUser sysUser = serviceFacade.getUserService().findSysUserById(userId);
		if (sysUser == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		List<SysPermission> sysPermissionList = new ArrayList<SysPermission>();
		Set sysRoles = sysUser.getSysRoles();
		Iterator iterator_sysRole = sysRoles.iterator();
		while (iterator_sysRole.hasNext()) {
			SysRole sysRole = (SysRole) iterator_sysRole.next();
			Set sysPermissions = sysRole.getSysPermissions();
			sysPermissionList.addAll(sysPermissions);
		}
		return sysPermissionList;
	}

	@Override
	public Menu findMenuByUserId(String userId) {
		if (StringUtils.isEmpty(userId)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		SysUser sysUser = serviceFacade.getUserService().findSysUserById(userId);
		if (sysUser == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 902, null));
		}
		
		Set<SysPermission> menuAll_set = new TreeSet<SysPermission>(new Comparator<SysPermission>() {

			@Override
			public int compare(SysPermission o1, SysPermission o2) {
				return o1.getShoworder().compareTo(o1.getShoworder());
			}
		});
		
		Set sysRoles = sysUser.getSysRoles();
		Iterator iterator_sysRole = sysRoles.iterator();
		while (iterator_sysRole.hasNext()) {
			SysRole sysRole = (SysRole) iterator_sysRole.next();
			Set sysPermissions = sysRole.getSysPermissions();
			Iterator iterator_sysPermission = sysPermissions.iterator();
			while (iterator_sysPermission.hasNext()) {
				SysPermission sysPermission = (SysPermission) iterator_sysPermission.next();
				String ismenu = sysPermission.getIsmenu();
				if (ismenu != null && "1".endsWith(ismenu)) {
					menuAll_set.add(sysPermission);
				}
			}
		}
		
		Menu menu = new Menu();
		List<Menu> fistMenus = new ArrayList<Menu>();
		Menu fistMenuObj = null;
		List<Menu> secondMenus = null;
		Iterator<SysPermission> iterator_menuAll = menuAll_set.iterator();
		while (iterator_menuAll.hasNext()) {
			SysPermission sysPermission = (SysPermission) iterator_menuAll.next();
			String plevel = sysPermission.getPlevel();
			if (StringUtils.isNotEmpty(plevel) && plevel == "1") {
				fistMenuObj = new Menu();
				fistMenuObj.setMenuid(sysPermission.getId());
				fistMenuObj.setMenuname(sysPermission.getName());
				fistMenuObj.setUrl(sysPermission.getUrl());
				secondMenus = new ArrayList<Menu>();
				fistMenuObj.setMenus(secondMenus);
				fistMenus.add(fistMenuObj);
			}else {
				Menu secondMenuObj = new Menu();
				secondMenuObj.setMenuid(sysPermission.getId());
				secondMenuObj.setMenuname(sysPermission.getName());
				secondMenuObj.setUrl(sysPermission.getUrl());
				secondMenus.add(secondMenuObj);
			}
		}
		menu.setMenus(fistMenus);
		return menu;
	}

}
