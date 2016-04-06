package com.frotly.yycg.base.auth.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.util.ResourcesUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 认证拦截器
 * @author Frotly
 *
 */
public class LoginInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		//当前访问地址是否允许不需要登录即可访问
		List<String> open_urls = ResourcesUtil.getkeyList(Config.ANONYMOUS_ACTIONS);
		String requestURI = request.getRequestURI();
		for (String open_url : open_urls) {
			if (requestURI.indexOf(open_url) >= 0) {
				return invocation.invoke();
			}
		}
		
		//当前用户是否登录
		ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		if (activeUser != null) {
			return invocation.invoke();
		}
		return "login";
	}

}
