package com.frotly.yycg.business.system.pojo;

import com.frotly.yycg.business.system.entity.SysUser;

/**
 * 对SysUser对象的扩展
 * @author Frotly
 *
 */
public class SysUserCustom extends SysUser {
	//扩展属性
	//用户类型id
	private String groupid;
	//用户状态
	private String userstate;
	//单位名称
	private String sysmc;

	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getUserstate() {
		return userstate;
	}
	public void setUserstate(String userstate) {
		this.userstate = userstate;
	}
	public String getSysmc() {
		return sysmc;
	}
	public void setSysmc(String sysmc) {
		this.sysmc = sysmc;
	}
}
