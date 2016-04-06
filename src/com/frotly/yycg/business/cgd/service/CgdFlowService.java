package com.frotly.yycg.business.cgd.service;

import java.util.List;

import com.frotly.yycg.business.cgd.pojo.TaskCustom;
import com.frotly.yycg.business.cgd.pojo.YycgdQueryVo;

/**
 * 采购单流程与activiti整合Service
 * @author Frotly
 *
 */
public interface CgdFlowService {
	/**
	 * 查询个人待办任务列表
	 * @param assignee 任务待办人id
	 * @param yycgdQueryVo 包装对象
	 * @param firstResult 本页第一条记录下标
	 * @param maxResults 本页最大记录数
	 * @return
	 */
	public List<TaskCustom> findPersonalTaskList(String assignee,YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults);
	
	/**
	 * 查询个人待办任务总数
	 * @param assignee 任务待办人id
	 * @param yycgdQueryVo包装对象
	 * @return
	 */
	public Long findPersonalTaskListCount(String assignee,YycgdQueryVo yycgdQueryVo);
	
	/**
	 * 查询组任务列表
	 * @param candidateUser 候选人
	 * @param yycgdQueryVo 包装对象
	 * @param firstResult 本页第一条记录下标
	 * @param maxResults 本页最大记录数
	 * @return
	 */
	public List<TaskCustom> findGroupTaskList(String candidateUser,YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults);
	
	/**
	 * 查询组任务总数
	 * @param candidateUser 候选人
	 * @param yycgdQueryVo包装对象
	 * @return
	 */
	public Long findGroupTaskListCount(String candidateUser,YycgdQueryVo yycgdQueryVo);
	
	/**
	 * 拾取任务，将组任务变为个人任务
	 * @param taskId 任务id
	 * @param assignee 待办人
	 */
	public void saveClaimTask(String taskId,String candidateUser);
}
