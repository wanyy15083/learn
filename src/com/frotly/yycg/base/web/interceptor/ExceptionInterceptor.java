package com.frotly.yycg.base.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.ServletActionContext;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.base.web.action.result.ExceptionResultInfo;
import com.frotly.yycg.base.web.action.result.ResultInfo;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor {
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		BaseAction baseAction = (BaseAction) actionInvocation.getAction();
		String result = null;
		try {
			result = actionInvocation.invoke();
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionResultInfo exceptionResultInfo = null;
			if (e instanceof ExceptionResultInfo) {
				exceptionResultInfo = (ExceptionResultInfo) e;
			}else if(e instanceof UnauthorizedException){
				ResultInfo resultInfo = ResultUtil.createFail(Config.MESSAGE, 105, null);
				exceptionResultInfo = new ExceptionResultInfo(resultInfo);
			}else {
				ResultInfo resultInfo = new ResultInfo();
				resultInfo.setType(0);
				resultInfo.setMessage("未知错误");
				exceptionResultInfo = new ExceptionResultInfo(resultInfo);
			}
			
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				baseAction.setProcessResult(exceptionResultInfo);
				result =  "error_json";
			}else {
				baseAction.setProcessResult(exceptionResultInfo);
				result =  "error_jsp";
			}
		}
		return result;
	}


}
