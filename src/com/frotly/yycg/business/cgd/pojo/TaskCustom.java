package com.frotly.yycg.business.cgd.pojo;

public class TaskCustom {

	//activiti的任务信息
	private String taskId;
	private String taskName;
	private String taskUrl;
	public String getTaskUrl() {
		return taskUrl;
	}
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	//业务系统采购单信息
	private YycgdCustom yycgdCustom;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public YycgdCustom getYycgdCustom() {
		return yycgdCustom;
	}
	public void setYycgdCustom(YycgdCustom yycgdCustom) {
		this.yycgdCustom = yycgdCustom;
	}
}
