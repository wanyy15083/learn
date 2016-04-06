package com.frotly.yycg.business.cgd.pojo;

import java.io.Serializable;

import com.frotly.yycg.business.cgd.entity.Yycgd;

public class YycgdCustom extends Yycgd implements Serializable{
	//activiti流程变量为pojo对象，实现Serializable接口，生成serialVersionUID
	private static final long serialVersionUID = 3017774987474923019L;
	
	//采购单编号
	private Integer yycgdbm;
	//采购单状态
	private String cgdzt;
	//审核结果
	private String checkResult;

	public Integer getYycgdbm() {
		return yycgdbm;
	}

	public void setYycgdbm(Integer yycgdbm) {
		this.yycgdbm = yycgdbm;
	}


	public String getCgdzt() {
		return cgdzt;
	}

	public void setCgdzt(String cgdzt) {
		this.cgdzt = cgdzt;
	}


	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	

}
