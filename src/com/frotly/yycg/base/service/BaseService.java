package com.frotly.yycg.base.service;

import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseService {
	@Autowired
	protected ServiceFacade serviceFacade;
}
