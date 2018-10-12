package com.misrobot.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.misrobot.workflow.common.BeanUtils;
import com.misrobot.workflow.common.Consts;
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
import com.misrobot.workflow.controller.response.HistoryAuditRecordRespose;
import com.misrobot.workflow.controller.response.QueryDeployedResponce;
import com.misrobot.workflow.controller.response.QueryProcessResponce;
import com.misrobot.workflow.controller.response.QueryTaskResponce;
import com.misrobot.workflow.controller.response.RejectTaskNode;
import com.misrobot.workflow.exception.ErrorCode;
import com.misrobot.workflow.exception.WorkflowException;
import com.misrobot.workflow.service.ProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "流程操作相关", tags = {"workflow"})
@RestController
@RequestMapping("process")
public class ProcessController {
	
	@Autowired
	private ProcessService processSv;

	@ApiOperation("查询已部署的流程定义列表")
	@GetMapping("queryDeployedProcessDefinition")
	public List<QueryDeployedResponce> queryDeployedProcessDefinition(@Validated QueryDeployedRequest reqBean) throws WorkflowException {
		return processSv.queryDeployedProcessDefinition(reqBean);
	}
	
	@ApiOperation("激活指定流程定义")
	@PostMapping("activeProcessDefinition")
	public void activeProcessDefinition(@Valid ActiveProcessDefinitionRequest reqBean) throws WorkflowException {
		processSv.activeProcessDefinition(reqBean);
	}
	
	@ApiOperation("挂起指定流程定义")
	@PostMapping("suspendProcessDefinition")
	public void suspendProcessDefinition(@Valid SuspendProcessDefinitionRequest reqBean) throws WorkflowException {
		processSv.suspendProcessDefinition(reqBean);
	}
	
	@ApiOperation("激活指定流程实例")
	@PostMapping("activeProcessInstance")
	public void activeProcessInstance(@Valid ActiveProcessInstanceRequest reqBean) throws WorkflowException {
		processSv.activeProcessInstance(reqBean);
	}
	
	@ApiOperation("挂起指定流程实例")
	@PostMapping("suspendProcessInstance")
	public void suspendProcessInstance(@Valid SuspendProcessInstanceRequest reqBean) throws WorkflowException {
		processSv.suspendProcessInstance(reqBean);
	}
	
	@ApiOperation(value = "启动流程")
	@PostMapping("startProcess")
	public void startProcess(@RequestBody @Valid StartProcessRequest reqBean) throws WorkflowException {
		processSv.startProcess(reqBean);
	}

//	@ApiIgnore
//	@ApiOperation("查询已启动的流程实例")
//	@GetMapping("queryStartedProcess")
//	public List<QueryStartedProcessResponce> queryStartedProcess(
//			@Valid QueryStartedProcessRequest reqBean) throws WorkflowException {
//		return processSv.queryStartedProcess(reqBean);
//	}

	@ApiOperation("查询代办任务")
	@GetMapping("queryTodoTask")
	public List<QueryTaskResponce> queryToDoTask(@Valid QueryTaskRequest reqBean) throws WorkflowException {
		List<QueryTaskResponce> resps = Lists.newArrayList();
		List<Task> toDoTask = processSv.queryToDoTask(reqBean);
		for (Task task : toDoTask) {
			QueryTaskResponce resp = new QueryTaskResponce();
			BeanUtils.copyProperties(task, resp);
			resp.setTaskVariables(task.getTaskLocalVariables());
			ProcessInstance processInstance = processSv.queryProcessInstance(task);
			resp.setBusinessKey(processInstance.getBusinessKey());
			resps.add(resp);
		}
		return resps;
	}
	
	@ApiOperation("查询当前用户发起的流程")
	@GetMapping("queryInitiatedProcess")
	public List<QueryProcessResponce> queryInitiatedProcess(@Valid QueryProcessRequest reqBean) throws WorkflowException {
		List<HistoricProcessInstance> processInstances = processSv.queryInitiatedProcessInstance(reqBean);
		List<QueryProcessResponce> resps = Lists.newArrayList();
		for (HistoricProcessInstance processInstance : processInstances) {
			QueryProcessResponce resp = new QueryProcessResponce();
			BeanUtils.copyProperties(processInstance, resp);
			resp.setProcessFinished(processInstance.getEndTime() != null);
			
			List<HistoricDetail> historicDetails = processSv.queryHistoricProcessVariableInstance(processInstance.getId());
			Map<String, Object> processVariables = historicDetails.stream().map(d->(HistoricVariableUpdate)d)
					.collect(Collectors.toMap(HistoricVariableUpdate::getVariableName, HistoricVariableUpdate::getValue));
			resp.setProcessVariables(processVariables);
			
			HistoricActivityInstance activityInstance = processSv.queryLatestHistoricActivityInstance(processInstance.getId());
			resp.setCurrentActivityName(activityInstance.getActivityName());
//			HistoricProcessInstance historicProcessInstance = processSv.queryHistoricProcessInstance(processInstance.getProcessInstanceId());
//			resp.setTaskVariables(processInstance.getTaskLocalVariables());
			resps.add(resp);
		}
		return resps;
	}
	
	@ApiOperation("查询当前用户相关的任务记录")
	@GetMapping("queryRelatedTask")
	public List<QueryTaskResponce> queryRelatedTask(@Valid QueryTaskRequest reqBean) throws WorkflowException {
		List<HistoricTaskInstance> taskInstances = processSv.queryRelatedTasks(reqBean);
		List<QueryTaskResponce> resps = Lists.newArrayList();
		for (HistoricTaskInstance historicTaskInstance : taskInstances) {
			QueryTaskResponce resp = new QueryTaskResponce();
			BeanUtils.copyProperties(historicTaskInstance, resp);
			resp.setTaskFinished(historicTaskInstance.getEndTime() != null);
			
			Map<String, Object> taskVariables = processSv.queryHistoricTaskVariableInstances(historicTaskInstance.getProcessInstanceId(), historicTaskInstance.getId())
					.stream().map(t->(HistoricVariableUpdate)t).collect(Collectors.toMap(HistoricVariableUpdate::getVariableName, HistoricVariableUpdate::getValue));
			resp.setTaskVariables(taskVariables);
			
			HistoricProcessInstance historicProcessInstance = processSv.queryHistoricProcessInstance(historicTaskInstance.getProcessInstanceId());
			resp.setBusinessKey(historicProcessInstance.getBusinessKey());
			resp.setProcessFinished(historicProcessInstance.getEndTime() != null);
			resp.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
			resp.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
			
			Map<String, Object> processInstanceVariables = processSv.queryHistoricProcessVariableInstance(historicProcessInstance.getId())
					.stream().map(t->(HistoricVariableUpdate)t).collect(Collectors.toMap(HistoricVariableUpdate::getVariableName, HistoricVariableUpdate::getValue));
			resp.setProcessVariables(processInstanceVariables);
			resp.setProcessInitiator((String)processInstanceVariables.get(Consts.PROCESS_INITIATOR));
			resps.add(resp);
		}
		return resps;
	}

	@ApiOperation("获取任务可驳回的节点")
	@GetMapping("getRejectTaskNodes")
	public List<RejectTaskNode> getRejectTaskNodes(@Valid RejectTaskRequest req) throws WorkflowException {
		return processSv.getRejectTaskNodes(req);
	}
	
	@ApiOperation("驳回任务到上一个节点")
	@PostMapping("rejectTaskToPreviousNode")
	public void rejectTaskToPreviousNode(@Valid RejectTaskRequest req) throws WorkflowException {
		processSv.rejectTaskToPreviousNode(req);
	}
	
	@ApiOperation("驳回任务到指定节点")
	@PostMapping("rejectTask")
	public void rejectTask(@Valid RejectTaskRequest req) throws WorkflowException {
		processSv.rejectTask(req);
	}
	
	/**
	 * 领取任务
	 * @param req
	 * @throws WorkflowException
	 */
	@ApiOperation("领取任务")
	@PostMapping("claimTask")
	public void claimTask(@Valid ClaimTaskRequest req) throws WorkflowException {
		processSv.claimTask(req);
	}
	
	/**
	 * 完成任务
	 * @param reqBean
	 * @return
	 * @throws WorkflowException
	 */
	@ApiOperation("完成任务")
	@PostMapping("completeTask")
	public void completeTask(@Valid @RequestBody CompleteTaskRequest reqBean) throws WorkflowException {
		processSv.completeTask(reqBean);
	}

	@ApiOperation("获取流程图")
	@GetMapping(value = "getProcessGraphics")
	public ResponseEntity<byte[]> getProcessGraphics(@Valid GetProcessGraphicsRequest reqBean, HttpServletResponse response) throws WorkflowException {
////		byte[] bytes = processSv.getProcessGraphics(reqBean);
////        return new ResponseEntity<byte[]>(bytes, HttpStatus.CREATED);
//		InputStream inputStream = processSv.getProcessGraphics(reqBean);
//		byte[] b = new byte[1024];
//        int len = -1;
//        while ((len = inputStream.read(b, 0, 1024)) != -1) {
//        	response.getOutputStream().write(b, 0, len);
//        }
		InputStream inputStream = processSv.getProcessGraphics(reqBean);
		byte[] b = new byte[1024];
        int len = -1;
        int length = 0;
        List<byte[]> byteList = Lists.newArrayList();
        try {
			while ((len = inputStream.read(b, 0, 1024)) != -1) {
			    
			    length += len;
				if (len == b.length) {
					byteList.add(b);
				} else {
					byte[] tmp = new byte[len];
					System.arraycopy(b, 0, tmp, 0, tmp.length);
					byteList.add(tmp);
				}
				b = new byte[1024];
			}
		} catch (IOException e) {
			throw new WorkflowException(ErrorCode.SYSTEM_ERROR);
		}
        byte[] bytes = new byte[length];
        length = 0;
        for (byte[] tmp : byteList) {
        	System.arraycopy(tmp, 0, bytes, length, tmp.length);
        	length = length + tmp.length;
        }
//        response.getOutputStream().write(bytes, 0, bytes.length);
        if (reqBean.isGraph()) {
        	return  ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
		} else {
			return  ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(bytes);
		}
	}

	@ApiOperation("获取流程的历史审核记录")
	@GetMapping(value = "queryHistoryAuditRecords")
	public List<HistoryAuditRecordRespose> queryHistoryAuditRecords(HistoryAuditRecordRequest req) {
		List<HistoryAuditRecordRespose> resps = Lists.newArrayList();
		
		List<HistoricTaskInstance> historyAuditRecords = processSv.queryHistoryAuditRecords(req);
		if (historyAuditRecords != null) {
			Map<String, Object> processVariables = null;
			for (HistoricTaskInstance instance : historyAuditRecords) {
				HistoryAuditRecordRespose resp = new HistoryAuditRecordRespose();
				BeanUtils.copyProperties(instance, resp);
					
				Map<String, Object> taskVariables = processSv.queryHistoricTaskVariableInstances(instance.getProcessInstanceId(), instance.getId())
						.stream().map(t->(HistoricVariableUpdate)t).collect(Collectors.toMap(HistoricVariableUpdate::getVariableName, HistoricVariableUpdate::getValue));
				resp.setTaskVariables(taskVariables);
				
				if (processVariables == null) {
					processVariables = processSv.queryHistoricProcessVariableInstance(instance.getProcessInstanceId())
							.stream().map(t->(HistoricVariableUpdate)t).collect(Collectors.toMap(HistoricVariableUpdate::getVariableName, HistoricVariableUpdate::getValue));
				}
				resp.setProcessVariables(processVariables);
				
				resps.add(resp);
			}
		}
		
		return resps;
	}
}
