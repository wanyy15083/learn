package com.frotly.yycg.base.auth.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frotly.yycg.base.web.action.result.ResultUtil;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger log = LoggerFactory
			.getLogger(CustomFormAuthenticationFilter.class);

	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (log.isTraceEnabled()) {
					log.trace("Login submission detected.  Attempting to execute login.");
				}
				HttpSession session = httpServletRequest.getSession();
				String validateCode_session = (String) session.getAttribute("validateCode");
				String validateCode = httpServletRequest.getParameter("validateCode");
				if (validateCode_session == null || validateCode == null || !validateCode.equals(validateCode_session)) {
					httpServletRequest.setAttribute("shiroLoginFailure", "validateCodeError");
					return true;
				}
				return executeLogin(request, response);
			} else {
				if (log.isTraceEnabled()) {
					log.trace("Login page view.");
				}
				return true;
			}
		} else {
			if (log.isTraceEnabled()) {
				log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
						+ "Authentication url [" + getLoginUrl() + "]");
			}

			saveRequestAndRedirectToLogin(request, response);
			return false;
		}
	}

	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		if(!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))){
			return super.onLoginSuccess(token, subject, request, response);
		}else {
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json;charset=UTF-8");
			httpServletResponse.getWriter().write("{\"resultInfo\":{\"type\":\"1\",\"messageCode\":\"906\",\"message\":\"登陆成功\"}}");
			return false;
		}
	}
	
	

}
