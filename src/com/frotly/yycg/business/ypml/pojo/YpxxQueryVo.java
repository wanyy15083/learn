package com.frotly.yycg.business.ypml.pojo;

import java.io.File;
import java.io.Serializable;

public class YpxxQueryVo implements Serializable {

	private YpxxCustom ypxxCustom;
	//分页参数,当前页码，和页面记录数
	private int page;
	private int rows;
	//导入的文件
	private File ypxximportfile;//名称和上传文件页面的file名称一致
	//导入文件的原始名称
	private String ypxximportfileFileName;// ypxximportfile和上传文件页面的file名称一致 后边的FileName是固定的

	
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

	public File getYpxximportfile() {
		return ypxximportfile;
	}

	public void setYpxximportfile(File ypxximportfile) {
		this.ypxximportfile = ypxximportfile;
	}

	public String getYpxximportfileFileName() {
		return ypxximportfileFileName;
	}

	public void setYpxximportfileFileName(String ypxximportfileFileName) {
		this.ypxximportfileFileName = ypxximportfileFileName;
	}

	public YpxxCustom getYpxxCustom() {
		return ypxxCustom;
	}

	public void setYpxxCustom(YpxxCustom ypxxCustom) {
		this.ypxxCustom = ypxxCustom;
	}
	
	
}
