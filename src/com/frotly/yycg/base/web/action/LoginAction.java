package com.frotly.yycg.base.web.action;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.base.web.action.result.SubmitResultInfo;

@Controller
@Scope("prototype")
public class LoginAction extends BaseAction{
	private String usercode;
	private String pwd;
	private String validateCode;
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
	public String login(){
		HttpSession session = this.getRequest().getSession();
		String validateCode_session = (String) session.getAttribute("validateCode");
		if (validateCode_session == null || validateCode == null || !validateCode_session.equals(validateCode)) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
		}
		ActiveUser activeUser = serviceFacade.getUserService().checkUser(usercode, pwd);
		session.setAttribute(Config.ACTIVEUSER_KEY, activeUser);
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 107, null));
		this.setProcessResult(submitResultInfo);
		return "login";
	}
	
	public String logout(){
		this.getRequest().getSession().invalidate();
		return "logout";
	}
}
