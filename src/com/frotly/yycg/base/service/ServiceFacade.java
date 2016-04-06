package com.frotly.yycg.base.service;

import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frotly.yycg.business.analyze.service.BusinessService;
import com.frotly.yycg.business.cgd.service.CgdFlowService;
import com.frotly.yycg.business.cgd.service.CgdService;
import com.frotly.yycg.business.system.service.AuthService;
import com.frotly.yycg.business.system.service.SystemService;
import com.frotly.yycg.business.system.service.UserService;
import com.frotly.yycg.business.ypml.service.YpxxService;

/**
 * Service门面，注入很多Service
 * @author Frotly
 *
 */
@Service
public class ServiceFacade {
	@Autowired
	private UserService userService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AuthService authService;
	@Autowired
	private CgdService cgdService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private CgdFlowService cgdFlowService;
	@Autowired
	private YpxxService ypxxService;
	@Autowired
	private BusinessService businessService;
	
	public BusinessService getBusinessService() {
		return businessService;
	}
	public YpxxService getYpxxService() {
		return ypxxService;
	}
	public CgdFlowService getCgdFlowService() {
		return cgdFlowService;
	}
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public UserService getUserService() {
		return userService;
	}
	public SystemService getSystemService() {
		return systemService;
	}
	public AuthService getAuthService() {
		return authService;
	}
	public CgdService getCgdService() {
		return cgdService;
	}
}
