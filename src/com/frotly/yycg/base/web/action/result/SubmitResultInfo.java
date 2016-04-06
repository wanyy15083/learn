package com.frotly.yycg.base.web.action.result;


/**
 * 系统提交结果结果类型
 * @author Thinkpad
 *
 */
public class SubmitResultInfo implements ProcessResult{

	public SubmitResultInfo(ResultInfo resultInfo){
		this.resultInfo = resultInfo;
	}
	
	//操作结果信息
	private ResultInfo resultInfo;
	
	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
		
}
