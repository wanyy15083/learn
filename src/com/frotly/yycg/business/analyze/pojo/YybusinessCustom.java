package com.frotly.yycg.business.analyze.pojo;

import java.util.Date;

import com.frotly.yycg.business.analyze.entity.Yybusiness;

public class YybusinessCustom extends Yybusiness {
	
	//区域id
	private String areaid;
	//区域名称
	private String areaname;
	
	//起始时间
	private Date startDate;
	//截止时间
	private Date endDate;
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	
}
