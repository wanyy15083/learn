package com.frotly.yycg.base.web.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.service.ServiceFacade;
import com.frotly.yycg.base.web.action.result.ProcessResult;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,ServletRequestAware{

	private T model;
	private Class<T> modelClass;
	private HttpServletRequest request;
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public T getModel() {
		return model;
	}
	@Autowired
	protected ServiceFacade serviceFacade;
	
	public BaseAction(){
		try {
			Type genericSuperclass = this.getClass().getGenericSuperclass();
			if (genericSuperclass != null && genericSuperclass instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
				Type type = parameterizedType.getActualTypeArguments()[0];
				modelClass = (Class<T>) type;
				model = modelClass.newInstance();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private ProcessResult processResult;
	public ProcessResult getProcessResult() {
		return processResult;
	}

	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}
}
