package com.frotly.yycg.business.cgd.web.action;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.ActiveUser;
import com.frotly.yycg.base.pojo.PageParameter;
import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.base.web.action.result.DataGridResultInfo;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.base.web.action.result.SubmitResultInfo;
import com.frotly.yycg.business.cgd.pojo.TaskCustom;
import com.frotly.yycg.business.cgd.pojo.YycgdQueryVo;

/**
 * 采购单业务流程和activiti整合action
 * @author Frotly
 *
 */
@Controller
@Scope("prototype")
public class CgdFlowAction extends BaseAction<YycgdQueryVo> {
	//进入待办任务列表页面
	public String tasklist(){
		
		return "tasklist";
	}
	
	//获得任务列表数据
	public String tasklist_result(){
		//获得页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		//获得当前登录用户
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String assignee = activeUser.getId();
		//获得分页参数
		long total = serviceFacade.getCgdFlowService().findPersonalTaskListCount(assignee, yycgdQueryVo);
		int page = yycgdQueryVo.getPage();
		int rows = yycgdQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		List<TaskCustom> taskCustomList = serviceFacade.getCgdFlowService().findPersonalTaskList(assignee, yycgdQueryVo, pageParameter.getPageQuery_star(), rows);
		
		//datagrid列表数据
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, taskCustomList);
		this.setProcessResult(dataGridResultInfo);
		
		return "tasklist_result";
	}
	
	//进入组任务列表页面
	public String grouptasklist(){
		
		return "grouptasklist";
	}
	//获取组任务列表数据
	public String grouptasklist_result(){
		//获得页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		//获得当前登录用户
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String candidateUser = activeUser.getId();
		//获得分页参数
		long total = serviceFacade.getCgdFlowService().findGroupTaskListCount(candidateUser, yycgdQueryVo);
		int page = yycgdQueryVo.getPage();
		int rows = yycgdQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		List<TaskCustom> taskCustomList = serviceFacade.getCgdFlowService().findGroupTaskList(candidateUser, yycgdQueryVo, pageParameter.getPageQuery_star(), rows);
		
		//datagrid列表数据
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, taskCustomList);
		this.setProcessResult(dataGridResultInfo);
		return "grouptasklist_result";
	}
	
	public String claimtask(){
		//获得页面数据
		YycgdQueryVo yycgdQueryVo = this.getModel();
		String taskId = yycgdQueryVo.getTaskId();
		//获得当前登录用户
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String candidateUser = activeUser.getId();
		serviceFacade.getCgdFlowService().saveClaimTask(taskId, candidateUser);
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
}







