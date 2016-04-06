package com.frotly.yycg.business.system.pojo;

import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.SysRole;

public class SysRoleCustom extends SysRole{
	
	private Dictinfo dictinfoByGroupid;

	public Dictinfo getDictinfoByGroupid() {
		return dictinfoByGroupid;
	}

	public void setDictinfoByGroupid(Dictinfo dictinfoByGroupid) {
		this.dictinfoByGroupid = dictinfoByGroupid;
	}
	
}
