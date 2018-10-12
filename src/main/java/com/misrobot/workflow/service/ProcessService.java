package com.misrobot.workflow.service;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.misrobot.workflow.controller.request.ActiveProcessDefinitionRequest;
import com.misrobot.workflow.controller.request.ActiveProcessInstanceRequest;
import com.misrobot.workflow.controller.request.ClaimTaskRequest;
import com.misrobot.workflow.controller.request.CompleteTaskRequest;
import com.misrobot.workflow.controller.request.GetProcessGraphicsRequest;
import com.misrobot.workflow.controller.request.HistoryAuditRecordRequest;
import com.misrobot.workflow.controller.request.QueryDeployedRequest;
import com.misrobot.workflow.controller.request.QueryProcessRequest;
import com.misrobot.workflow.controller.request.QueryTaskRequest;
import com.misrobot.workflow.controller.request.RejectTaskRequest;
import com.misrobot.workflow.controller.request.StartProcessRequest;
import com.misrobot.workflow.controller.request.SuspendProcessDefinitionRequest;
import com.misrobot.workflow.controller.request.SuspendProcessInstanceRequest;
import com.misrobot.workflow.controller.response.QueryDeployedResponce;
import com.misrobot.workflow.controller.response.RejectTaskNode;
import com.misrobot.workflow.exception.WorkflowException;

public interface ProcessService {
//	/**
//	 * 部署流程
//	 */
//	public ProcessDeployResponce deployProcess(ProcessDeployRequest reqBean) ;

	/**
	 * 已部署流程列表
	 */
	public List<QueryDeployedResponce> queryDeployedProcessDefinition(QueryDeployedRequest reqBean);
	
	/**
	 * 挂起流程定义
	 * @param reqBean
	 */
	public void suspendProcessDefinition(SuspendProcessDefinitionRequest reqBean) throws WorkflowException;
	
	/**
	 * 激活流程定义（解挂流程定义）
	 * @param reqBean
	 * @throws WorkflowException
	 */
	public void activeProcessDefinition(ActiveProcessDefinitionRequest reqBean) throws WorkflowException;
	
	/**
	 * 挂起流程实例
	 * @param reqBean
	 */
	public void suspendProcessInstance(SuspendProcessInstanceRequest reqBean) throws WorkflowException;
	
	/**
	 * 激活流程实例（解挂流程实例）
	 * @param reqBean
	 */
	public void activeProcessInstance(ActiveProcessInstanceRequest reqBean) throws WorkflowException;

	/**
	 * 启动流程
	 * @throws WorkflowException 
	 */
	public void startProcess(StartProcessRequest reqBean) throws WorkflowException;

//	/**
//	 * 所有指定条件的流程实例
//	 */
//	public List<QueryStartedProcessResponce> queryStartedProcess(QueryStartedProcessRequest reqBean) ;

	/**
	 * 查询用户的代办任务。该任务还不属于用户
	 * @param reqBean
	 * @return
	 */
	public List<Task> queryToDoTask(QueryTaskRequest reqBean);
	
	public ProcessInstance queryProcessInstance(Task task);
	
	/**
	 * 查询用户发起的流程
	 */
	public List<HistoricProcessInstance> queryInitiatedProcessInstance(QueryProcessRequest reqBean) ;

	public HistoricActivityInstance queryLatestHistoricActivityInstance(String processInstanceId);
	
	/**
	 * 查询用户的相关任务，包括已经完成与未完成的
	 * @param reqBean
	 * @return
	 */
	public List<HistoricTaskInstance> queryRelatedTasks(QueryTaskRequest reqBean);
	
	public HistoricProcessInstance queryHistoricProcessInstance(String processInstanceId);
	
	/**
	 * 任务完成
	 * @throws WorkflowException 
	 */
	public void completeTask(CompleteTaskRequest reqB) throws WorkflowException ;
	
	/**
	 * 任务领取
	 * @throws WorkflowException 
	 */
	public void claimTask(ClaimTaskRequest req) throws WorkflowException ;

	/**
	 * 获取流程图图形字节数组
	 * @param reqBean
	 * @return
	 * @throws WorkflowException 
	 */
	public InputStream getProcessGraphics(GetProcessGraphicsRequest reqBean) throws WorkflowException;
	
	/**
	 * 获取可以驳回任务的所有节点
	 * @param req
	 * @return
	 * @throws WorkflowException
	 */
	public List<RejectTaskNode> getRejectTaskNodes(RejectTaskRequest req) throws WorkflowException ;
	
	/**
	 * 驳回任务到上一个节点
	 * @param req
	 * @throws WorkflowException 
	 */
	void rejectTaskToPreviousNode(RejectTaskRequest req) throws WorkflowException;
	
	/**
	 * 驳回任务到指定节点
	 * @param req
	 * @throws WorkflowException
	 */
	public void rejectTask(RejectTaskRequest req) throws WorkflowException ;


	/**
	 * 查询历史审核记录
	 * @param req
	 * @return
	 */
	List<HistoricTaskInstance> queryHistoryAuditRecords(HistoryAuditRecordRequest req);

	/**
	 * 查询任务的历史变量。无法直接通过taskId查找变量，只能传两个参数查找
	 * @param instance
	 * @return
	 */
	List<HistoricDetail> queryHistoricTaskVariableInstances(String processInstanceId, String taskId);

	/**
	 * 查询流程实例的历史变量（启动流程的变量）
	 * @param processInstanceId
	 * @return
	 */
	List<HistoricDetail> queryHistoricProcessVariableInstance(String processInstanceId);
}
