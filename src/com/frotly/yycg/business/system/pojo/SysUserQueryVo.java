package com.frotly.yycg.business.system.pojo;

import java.util.List;

import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.SysUser;

/**
 * SysUser的包装对象,扩展对象放在其中
 * @author Frotly
 *
 */
public class SysUserQueryVo {
	//扩展对象
	private SysUserCustom sysUserCustom;
	//分页参数,当前页码，和页面记录数
	private int page;
	private int rows;
	//用户类别列表
	private List<Dictinfo> groupList;
	//用户状态
	private List<Dictinfo> userstateList;
	
	public List<Dictinfo> getUserstateList() {
		return userstateList;
	}
	public void setUserstateList(List<Dictinfo> userstateList) {
		this.userstateList = userstateList;
	}
	public List<Dictinfo> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Dictinfo> groupList) {
		this.groupList = groupList;
	}
	public SysUserCustom getSysUserCustom() {
		return sysUserCustom;
	}
	public void setSysUserCustom(SysUserCustom sysUserCustom) {
		this.sysUserCustom = sysUserCustom;
	}
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
	
}
