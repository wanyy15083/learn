package com.frotly.yycg.business.system.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.frotly.yycg.base.config.Config;
import com.frotly.yycg.base.pojo.PageParameter;
import com.frotly.yycg.base.web.action.BaseAction;
import com.frotly.yycg.base.web.action.result.DataGridResultInfo;
import com.frotly.yycg.base.web.action.result.ResultUtil;
import com.frotly.yycg.base.web.action.result.SubmitResultInfo;
import com.frotly.yycg.business.system.pojo.FlowQueryVo;

/**
 * activiti流程部署action
 * @author Frotly
 *
 */
@Controller
@Scope("prototype")
public class FlowAction extends BaseAction<FlowQueryVo> {

	//进入流程定义列表页面
	public String processdeflist(){
		
		return "processdeflist";
	}
	//流程定义列表数据
	public String processdeflist_result(){
		//获取页面数据
		FlowQueryVo flowQueryVo = this.getModel();
		//获得service
		RepositoryService repositoryService = serviceFacade.getProcessEngine().getRepositoryService();
		//获得流程定义查询对象
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		//指定查询条件
		processDefinitionQuery.processDefinitionKey(Config.FLOW_KEY_PURCHASING);
		processDefinitionQuery.orderByProcessDefinitionVersion().desc();
		//获得查询数据
		long total = processDefinitionQuery.count();
		int page = flowQueryVo.getPage();
		int rows = flowQueryVo.getRows();
		PageParameter pageParameter = new PageParameter(page, rows, total);
		List<ProcessDefinition> list = processDefinitionQuery.listPage(pageParameter.getPageQuery_star(), rows);
		
		//datagrid列表数据
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, list);
		this.setProcessResult(dataGridResultInfo);
		return "processdeflist_result";
	}
	
	//进入部署流程定义页面
	public String deployprocess(){
		
		return "deployprocess";
	}
	//部署流程定义
	public String deployprocess_submit(){
		//获取页面数据
		FlowQueryVo flowQueryVo = this.getModel();
		File resource_bpmn = flowQueryVo.getResource_bpmn();
		String resource_bpmnFileName = flowQueryVo.getResource_bpmnFileName();
		File resource_png = flowQueryVo.getResource_png();
		String resource_pngFileName = flowQueryVo.getResource_pngFileName();
		//获得RepositoryService
		RepositoryService repositoryService = serviceFacade.getProcessEngine().getRepositoryService();
		//创建流程部署对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		
		//添加部署文件，获取输入流
		try {
			deploymentBuilder.addInputStream(resource_bpmnFileName, new FileInputStream(resource_bpmn));
			deploymentBuilder.addInputStream(resource_pngFileName, new FileInputStream(resource_png));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//执行部署，即上传
		deploymentBuilder.deploy();
		
		//返回操作成功提示信息
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
		this.setProcessResult(submitResultInfo);
		return "processResult";
	}
	
}
