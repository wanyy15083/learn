package com.frotly.yycg.business.system.entity;

// Generated 2015-12-4 21:43:10 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Dictinfo generated by hbm2java
 */
public class Dictinfo implements java.io.Serializable {

	private String id;
	private String dictcode;
	private String typecode;
	private String info;
	private String remark;
	private String updatetime;
	private Integer dictsort;
	private String isenable;
	private String valuetype;
//	private Set sysUsersForGroupid = new HashSet(0);
//	private Set sysUsersForUserstate = new HashSet(0);



	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictcode() {
		return this.dictcode;
	}

	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}

	public String getTypecode() {
		return this.typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getDictsort() {
		return this.dictsort;
	}

	public void setDictsort(Integer dictsort) {
		this.dictsort = dictsort;
	}

	public String getIsenable() {
		return this.isenable;
	}

	public void setIsenable(String isenable) {
		this.isenable = isenable;
	}

	public String getValuetype() {
		return this.valuetype;
	}

	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}


}
