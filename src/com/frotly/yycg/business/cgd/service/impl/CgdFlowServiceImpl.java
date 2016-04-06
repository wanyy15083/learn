package com.frotly.yycg.business.cgd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.dao.BaseDao;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.service.BaseService;
import com.frotly.yycg.business.cgd.entity.Yycgd;
import com.frotly.yycg.business.cgd.pojo.TaskCustom;
import com.frotly.yycg.business.cgd.pojo.YycgdCustom;
import com.frotly.yycg.business.cgd.pojo.YycgdQueryVo;
import com.frotly.yycg.business.cgd.service.CgdFlowService;
import com.frotly.yycg.business.system.entity.Dictinfo;
import com.frotly.yycg.business.system.entity.Userjd;

@Service
public class CgdFlowServiceImpl extends BaseService implements CgdFlowService {
	
	@Resource(name="userjdDao")
	private BaseDao<Userjd> userjdDao;
	
	@Override
	public List<TaskCustom> findPersonalTaskList(String assignee,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults) {
		//获得Service
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		RuntimeService runtimeService = serviceFacade.getProcessEngine().getRuntimeService();
		FormService formService = serviceFacade.getProcessEngine().getFormService();
		//创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		//绑定查询条件
		taskQuery.taskAssignee(assignee);
		taskQuery.processDefinitionKey(Config.FLOW_KEY_PURCHASING);
		//获得查询结果
		List<Task> taskList = taskQuery.listPage(firstResult, maxResults);
		List<TaskCustom> taskCustomList = new ArrayList<TaskCustom>();
		for (Task task : taskList) {
			TaskCustom taskCustom = new TaskCustom();
			String processInstanceId = task.getProcessInstanceId();
			//获得当前流程实例对象
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			//获得businessKey
			String businessKey = processInstance.getBusinessKey();
			Integer yycgdid = Integer.parseInt(businessKey);
			//获得yycgdCustom
			Yycgd yycgd = serviceFacade.getCgdService().findYycgdById(yycgdid);
			YycgdCustom yycgdCustom = new YycgdCustom();
			BeanUtils.copyProperties(yycgd, yycgdCustom);
			taskCustom.setYycgdCustom(yycgdCustom);
			taskCustom.setTaskId(task.getId());
			taskCustom.setTaskName(task.getName());
			//获得taskUrl
			TaskFormData taskFormData = formService.getTaskFormData(task.getId());
			taskCustom.setTaskUrl(taskFormData.getFormKey());
			taskCustomList.add(taskCustom);
		}
		return taskCustomList;
	}

	@Override
	public Long findPersonalTaskListCount(String assignee,
			YycgdQueryVo yycgdQueryVo) {
		//获得Service
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		//创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		//绑定查询条件
		taskQuery.taskAssignee(assignee);
		taskQuery.processDefinitionKey(Config.FLOW_KEY_PURCHASING);
		return taskQuery.count();
	}

	@Override
	public List<TaskCustom> findGroupTaskList(String candidateUser,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults) {

		//获得Service
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		RuntimeService runtimeService = serviceFacade.getProcessEngine().getRuntimeService();
		FormService formService = serviceFacade.getProcessEngine().getFormService();
		//创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		//绑定查询条件
		taskQuery.taskCandidateUser(candidateUser);
		taskQuery.processDefinitionKey(Config.FLOW_KEY_PURCHASING);
		//只能审核当前用户管辖区域的采购单,匹配流程变量
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String sysid = activeUser.getSysid();
		String groupid = activeUser.getGroupid();
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoById(groupid);
		if ("2".equals(dictinfo.getDictcode())) {
			Userjd userjd = userjdDao.findById(sysid);
			String userjd_areaid = userjd.getDq();
			taskQuery.processVariableValueEquals("userjd_areaid", userjd_areaid);
		}else if ("4".equals(dictinfo.getDictcode())) {
			taskQuery.processVariableValueEquals("usergysid", sysid);
		}
		
		//获得查询结果
		List<Task> taskList = taskQuery.listPage(firstResult, maxResults);
		List<TaskCustom> taskCustomList = new ArrayList<TaskCustom>();
		for (Task task : taskList) {
			TaskCustom taskCustom = new TaskCustom();
			String processInstanceId = task.getProcessInstanceId();
			//获得当前流程实例对象
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			//获得businessKey
			String businessKey = processInstance.getBusinessKey();
			Integer yycgdid = Integer.parseInt(businessKey);
			//获得yycgdCustom
			Yycgd yycgd = serviceFacade.getCgdService().findYycgdById(yycgdid);
			YycgdCustom yycgdCustom = new YycgdCustom();
			BeanUtils.copyProperties(yycgd, yycgdCustom);
			taskCustom.setYycgdCustom(yycgdCustom);
			taskCustom.setTaskId(task.getId());
			taskCustom.setTaskName(task.getName());
			//获得taskUrl
			TaskFormData taskFormData = formService.getTaskFormData(task.getId());
			taskCustom.setTaskUrl(taskFormData.getFormKey());
			taskCustomList.add(taskCustom);
		}
		return taskCustomList;
	}

	@Override
	public Long findGroupTaskListCount(String candidateUser,
			YycgdQueryVo yycgdQueryVo) {
		//获得Service
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		//创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		//绑定查询条件
		taskQuery.taskCandidateUser(candidateUser);
		taskQuery.processDefinitionKey(Config.FLOW_KEY_PURCHASING);
		return taskQuery.count();
	}

	@Override
	public void saveClaimTask(String taskId, String candidateUser) {
		//获得Service
		TaskService taskService = serviceFacade.getProcessEngine().getTaskService();
		//创建查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.taskCandidateUser(candidateUser).taskId(taskId).singleResult();
		if (task != null) {
			taskService.claim(taskId, candidateUser);
		}
	}

}
