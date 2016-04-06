package com.frotly.yycg.base.pojo;

import java.io.Serializable;
import java.util.List;

import com.frotly.yycg.business.system.entity.SysPermission;

/**
 *  用户身份信息
 */
public class ActiveUser implements Serializable{
	
	private String id;//用户id
	private String usercode;//用户账号
	private String username;//用户名称
	private String groupid;//用户类型
	private String groupname;//用户类型名称

//	private Menu menu;//操作菜单
	private List<SysPermission> sysPermissionList;//操作权限，包括用户点击菜单及操作菜单功能所有链接权限
	
	private String sysid;//用户所属单位id
	private String sysmc;//单位名称
	
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
//	public Menu getMenu() {
//		return menu;
//	}
//	public void setMenu(Menu menu) {
//		this.menu = menu;
//	}
//
	public List<SysPermission> getSysPermissionList() {
		return sysPermissionList;
	}
	public void setSysPermissionList(List<SysPermission> sysPermissionList) {
		this.sysPermissionList = sysPermissionList;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getSysmc() {
		return sysmc;
	}
	public void setSysmc(String sysmc) {
		this.sysmc = sysmc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

		
}
