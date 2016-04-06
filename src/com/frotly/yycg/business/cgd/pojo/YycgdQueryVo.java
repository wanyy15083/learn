package com.frotly.yycg.business.cgd.pojo;

import java.util.List;

import com.frotly.yycg.business.cgd.entity.Yycgd;
import com.frotly.yycg.business.cgd.entity.Yycgdmx;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.ypml.pojo.YpxxCustom;

public class YycgdQueryVo {
	//分页参数,当前页码，和页面记录数
	private int page;
	private int rows;
	private YycgdCustom yycgdCustom;
	private Yycgd yycgd;
	//用户状态
	private List<Dictinfo> cgdztList;
	//药品信息扩展对象
	private YpxxCustom ypxxCustom;
	//采购单明细，添加药品信息的id串
	private String ypxxids;
	//选中药品信息行的序号
	private String indexs;
	//采购单明细list
	private List<Yycgdmx> yycgdmxList;
	//药品类别
	private List<Dictinfo> yplbList;
	//交易状态
	private List<Dictinfo> jyztList;
	//质量层次
	private List<Dictinfo> ypzlccList;
	//采购单受理状态
	private List<Dictinfo> cgztList;
	public List<Dictinfo> getCgztList() {
		return cgztList;
	}
	public void setCgztList(List<Dictinfo> cgztList) {
		this.cgztList = cgztList;
	}
	//taskCustom
	private TaskCustom taskCustom;
	private String taskId;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public TaskCustom getTaskCustom() {
		return taskCustom;
	}
	public void setTaskCustom(TaskCustom taskCustom) {
		this.taskCustom = taskCustom;
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

	public YycgdCustom getYycgdCustom() {
		return yycgdCustom;
	}
	public void setYycgdCustom(YycgdCustom yycgdCustom) {
		this.yycgdCustom = yycgdCustom;
	}

	public Yycgd getYycgd() {
		return yycgd;
	}
	public void setYycgd(Yycgd yycgd) {
		this.yycgd = yycgd;
	}

	public List<Dictinfo> getCgdztList() {
		return cgdztList;
	}
	public void setCgdztList(List<Dictinfo> cgdztList) {
		this.cgdztList = cgdztList;
	}

	public YpxxCustom getYpxxCustom() {
		return ypxxCustom;
	}
	public void setYpxxCustom(YpxxCustom ypxxCustom) {
		this.ypxxCustom = ypxxCustom;
	}

	public String getYpxxids() {
		return ypxxids;
	}
	public void setYpxxids(String ypxxids) {
		this.ypxxids = ypxxids;
	}

	public String getIndexs() {
		return indexs;
	}
	public void setIndexs(String indexs) {
		this.indexs = indexs;
	}

	public List<Yycgdmx> getYycgdmxList() {
		return yycgdmxList;
	}
	public void setYycgdmxList(List<Yycgdmx> yycgdmxList) {
		this.yycgdmxList = yycgdmxList;
	}
	public List<Dictinfo> getYplbList() {
		return yplbList;
	}
	public void setYplbList(List<Dictinfo> yplbList) {
		this.yplbList = yplbList;
	}
	public List<Dictinfo> getJyztList() {
		return jyztList;
	}
	public void setJyztList(List<Dictinfo> jyztList) {
		this.jyztList = jyztList;
	}
	public List<Dictinfo> getYpzlccList() {
		return ypzlccList;
	}
	public void setYpzlccList(List<Dictinfo> ypzlccList) {
		this.ypzlccList = ypzlccList;
	}
}
