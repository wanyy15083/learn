package com.frotly.yycg.business.system.service;

import java.util.List;

import com.frotly.yycg.base.pojo.Menu;
import com.frotly.yycg.business.system.entity.SysPermission;
import com.frotly.yycg.business.system.entity.SysRole;
import com.frotly.yycg.business.system.pojo.SysRoleQueryVo;
import com.frotly.yycg.business.system.pojo.SysUserQueryVo;

/**
 * 用户权限相关
 * @author Frotly
 *
 */
public interface AuthService {
	//查询用户角色
	public List<SysRole> findSysRoleList(SysRoleQueryVo sysRoleQueryVo,int firstResult,int maxResults);
	//查询角色个数
	public Long findSysRoleCount();
	//查询权限数据
	public List<SysPermission> findSysPermissionList();
	//根据角色查询权限
	public List<SysPermission> findSysPermissionByRoleId(String roleId);
	//保存角色权限
	public void saveRoleAuthorize(String roleId,List<String> sysPermissionids);
	//根据用户id查询用户权限
	public List<SysPermission> findPermissionListByUserId(String userId);
	//根据登录用户id获取权限菜单
	public Menu findMenuByUserId(String userId);
}
