package com.frotly.yycg.business.system.pojo;

import java.util.List;

import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.SysPermission;

/**
 * 用户角色包装类
 * @author Frotly
 *
 */
public class SysRoleQueryVo {
	//分页参数,当前页码，和页面记录数
	private int page;
	private int rows;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//角色id
	private String roleId;
	//角色下的权限
	private String sysPermissionsByRoleId;
	//所有权限
	private List<SysPermission> sysPermissionList;
	//扩展对象
	private SysRoleCustom sysRoleCustom;
	
	public SysRoleCustom getSysRoleCustom() {
		return sysRoleCustom;
	}
	public void setSysRoleCustom(SysRoleCustom sysRoleCustom) {
		this.sysRoleCustom = sysRoleCustom;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getSysPermissionsByRoleId() {
		return sysPermissionsByRoleId;
	}
	public void setSysPermissionsByRoleId(String sysPermissionsByRoleId) {
		this.sysPermissionsByRoleId = sysPermissionsByRoleId;
	}
	public List<SysPermission> getSysPermissionList() {
		return sysPermissionList;
	}
	public void setSysPermissionList(List<SysPermission> sysPermissionList) {
		this.sysPermissionList = sysPermissionList;
	}
	
	private List<Dictinfo> groupList;
	public List<Dictinfo> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Dictinfo> groupList) {
		this.groupList = groupList;
	}
}
