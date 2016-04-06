package com.frotly.yycg.business.system.pojo;

import java.io.File;

public class FlowQueryVo {
	//分页参数
	private Integer page;
	private Integer rows;
	//上传文件，部署流程，bpmn和png
	private File resource_bpmn;
	private File resource_png;
	//原始文件名称
	private String resource_bpmnFileName;
	private String resource_pngFileName;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public File getResource_bpmn() {
		return resource_bpmn;
	}
	public void setResource_bpmn(File resource_bpmn) {
		this.resource_bpmn = resource_bpmn;
	}
	public File getResource_png() {
		return resource_png;
	}
	public void setResource_png(File resource_png) {
		this.resource_png = resource_png;
	}
	public String getResource_bpmnFileName() {
		return resource_bpmnFileName;
	}
	public void setResource_bpmnFileName(String resource_bpmnFileName) {
		this.resource_bpmnFileName = resource_bpmnFileName;
	}
	public String getResource_pngFileName() {
		return resource_pngFileName;
	}
	public void setResource_pngFileName(String resource_pngFileName) {
		this.resource_pngFileName = resource_pngFileName;
	}
	
	
}
