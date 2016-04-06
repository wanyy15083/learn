package com.frotly.yycg.business.analyze.pojo;

import java.io.Serializable;

public class YybusinssQueryVo implements Serializable {
	
	//jfreechart生成图形名称
	private String jfreechart_filename;
	
	private YybusinessCustom yybusinessCustom;
	
	

	public YybusinessCustom getYybusinessCustom() {
		return yybusinessCustom;
	}

	public void setYybusinessCustom(YybusinessCustom yybusinessCustom) {
		this.yybusinessCustom = yybusinessCustom;
	}

	public String getJfreechart_filename() {
		return jfreechart_filename;
	}

	public void setJfreechart_filename(String jfreechart_filename) {
		this.jfreechart_filename = jfreechart_filename;
	}
	
	
	
}
