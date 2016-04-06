package com.frotly.yycg.base.auth.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.business.system.entity.SysPermission;
import com.frotly.yycg.util.ResourcesUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限拦截器
 * @author Frotly
 *
 */
public class PermissionInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String requestURI = request.getRequestURI();
		//无需登录即可访问
		List<String> open_urls = ResourcesUtil.getkeyList(Config.ANONYMOUS_ACTIONS);
		for (String open_url : open_urls) {
			if (requestURI.indexOf(open_url) >= 0) {
				return invocation.invoke();
			}
		}
		//登录后即可访问
		List<String> common_urls = ResourcesUtil.getkeyList(Config.COMMON_ACTIONS);
		for (String common_url : common_urls) {
			if (requestURI.indexOf(common_url) >= 0) {
				return invocation.invoke();
			}
		}
		//拥有权限
		//ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		List<SysPermission> sysPermissionList = activeUser.getSysPermissionList();
		for (SysPermission sysPermission : sysPermissionList) {
			String url = sysPermission.getUrl();
			if (StringUtils.isNotEmpty(url)) {
				if (requestURI.indexOf(url) >= 0) {
					return invocation.invoke();
				}
			}
		}
		return "refuse";
	}
	
}
