package com.frotly.yycg.base.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.web.action.result.ResultUtil;


@Controller
@Scope("prototype")
public class ShiroLoginAction extends BaseAction {
	
	public String login(){
		String exceptionClassName = (String) this.getRequest().getAttribute("shiroLoginFailure");
		if (exceptionClassName != null) {
			//解析异常信息
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				//抛出帐号不存在异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 101, null));
			}else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				//抛出不正确凭据异常,即用户名或密码错误
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 114, null));
			}else if ("validateCodeError".equals(exceptionClassName)) {
				//验证码错误异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
			}
		}
		return "login";
	}
}
