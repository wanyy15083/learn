package com.frotly.yycg.business.ypml.pojo;

import com.frotly.yycg.business.ypml.entity.Ypxx;

public class YpxxCustom extends Ypxx {

	//药品类别
	private String lb;
	//交易状态
	private String jyzt;
	//价格最小值
	private Float zbjglower;
	//价格最大值
	private Float zbjgupper;
	
	public Float getZbjglower() {
		return zbjglower;
	}
	public void setZbjglower(Float zbjglower) {
		this.zbjglower = zbjglower;
	}
	public Float getZbjgupper() {
		return zbjgupper;
	}
	public void setZbjgupper(Float zbjgupper) {
		this.zbjgupper = zbjgupper;
	}
	public String getLb() {
		return lb;
	}
	public void setLb(String lb) {
		this.lb = lb;
	}
	public String getJyzt() {
		return jyzt;
	}
	public void setJyzt(String jyzt) {
		this.jyzt = jyzt;
	}
}
