package com.frotly.yycg.base.web.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.pojo.ActiveUser;

@Controller
@Scope("prototype")
public class FirstAction extends BaseAction {

	private ActiveUser activeUser;
	
	public ActiveUser getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(ActiveUser activeUser) {
		this.activeUser = activeUser;
	}
	
	public String first(){
		Subject subject = SecurityUtils.getSubject();
		activeUser = (ActiveUser) subject.getPrincipal();
		return "first";
	}
	public String welcome(){
		return "welcome";
	}
}
