package com.misrobot.workflow.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.misrobot.workflow.common.BeanUtils;
import com.misrobot.workflow.common.Consts;
import com.misrobot.workflow.controller.request.ActiveProcessDefinitionRequest;
import com.misrobot.workflow.controller.request.ActiveProcessInstanceRequest;
import com.misrobot.workflow.controller.request.CancelProcessRequest;
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
import com.misrobot.workflow.controller.request.WithdrawTaskRequest;
import com.misrobot.workflow.controller.response.PageableResponseBean;
import com.misrobot.workflow.controller.response.QueryDeployedResponce;
import com.misrobot.workflow.controller.response.RejectTaskNode;
import com.misrobot.workflow.exception.ErrorCode;
import com.misrobot.workflow.exception.WorkflowException;
import com.misrobot.workflow.model.WfTaskwithdrawSetting;
import com.misrobot.workflow.repository.WfTaskwithdrawSettingRepository;
import com.misrobot.workflow.service.ProcessService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

//	@Autowired
//	ProcessEngine engine;
//	@Autowired
//	ActIdTaskDao idTaskDao;
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private WfTaskwithdrawSettingRepository taskwithdrawSettingRepository;
	
	private int getMaxResult(Integer size) {
		if (size != null && size >= 1) {
			return size;
		}
		return Consts.MAX_RESULT;
	}

	private int getFirstResult(Integer page, Integer size) {
		if (size != null && page != null && page >= 1 && size >= 1) {
			return (page - 1) * size;
		}
		return Consts.FIRST_RESULT;
	}
	
	public void startProcess(StartProcessRequest reqBean) throws WorkflowException {
		
		try {
			Map<String, Object> processVariables = new HashMap<>();
			if (reqBean.getProcessVariables() != null) {
				processVariables.putAll(reqBean.getProcessVariables());
			}
			
			//TODO: 需要增加当前登录用户为启动任务的用户
			processVariables.put(Consts.PROCESS_INITIATOR, "user");
			
			runtimeService.startProcessInstanceByKeyAndTenantId(reqBean.getProcessDefinitionKey(),
					reqBean.getBusinessKey(), processVariables, reqBean.getSystemCode());
			
		} catch (ActivitiObjectNotFoundException e) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_INSTANCE, reqBean.getProcessDefinitionKey());
		}
	}

//	public PageableResponseBean<List<QueryStartedProcessResponce>> queryStartedProcess(QueryStartedProcessRequest reqBean) {
//
//		//
//		ProcessInstanceQuery processQuery = runtimeService.createProcessInstanceQuery();
//		if (StringUtils.hasText(reqBean.getProcessDefinitionId())) {
//			processQuery.processDefinitionId(reqBean.getProcessDefinitionId());
//		}
//		if (StringUtils.hasText(reqBean.getProcessInstanceId())) {
//			processQuery.processInstanceId(reqBean.getProcessInstanceId());
//		}
//		if (StringUtils.hasText(reqBean.getBusinessKey())) {
//			processQuery.processInstanceBusinessKey(reqBean.getBusinessKey());
//		}
//		if (StringUtils.hasText(reqBean.getSystemCode())) {
//			processQuery.processInstanceTenantId(reqBean.getSystemCode());
//		}
//		//
//		long count = processQuery.count();
//		List<QueryStartedProcessResponce> resBean = Lists.newArrayList();
//		if (count > 0) {
//			processQuery.orderByProcessInstanceId();
//			processQuery.desc();
//
//			List<ProcessInstance> list = processQuery.listPage(
//					getFirstResult(reqBean.getPage(), reqBean.getSize()), 
//					getMaxResult(reqBean.getSize()));
//
//			if (list != null) {
//				for (int i = 0; i < list.size(); i++) {
//					ProcessInstance def = list.get(i);
//					if (def == null) {
//						continue;
//					}
//					QueryStartedProcessResponce body = new QueryStartedProcessResponce();
//					BeanUtils.copyProperties(def, body);
//					body.setProcessVariables(runtimeService.getVariablesLocal(def.getId()));
//					
//					resBean.add(body);
//				}
//			}
//		}
//
//		return resBean;
//	}

	/*
	 * 已部署流程列表
	 */
	public PageableResponseBean<List<QueryDeployedResponce>> queryDeployedProcessDefinition(QueryDeployedRequest reqBean) {
		
		ProcessDefinitionQuery defQuery = repositoryService.createProcessDefinitionQuery();
//		// 多租户模式
		if (StringUtils.hasText(reqBean.getSystemCode())) {
			defQuery.processDefinitionTenantId(reqBean.getSystemCode());
		}
		if (reqBean.isOnlyShowLatestedVersionData()) {
			defQuery.latestVersion();
		}
		//TODO: 使用version过滤就会只显示最新版本的数据，后续需要对key进行分组过滤
		
		//
		long count = defQuery.count();
		List<QueryDeployedResponce> resBodyList = Lists.newArrayList();
		
		PageableResponseBean<List<QueryDeployedResponce>> resp = new PageableResponseBean<>();
		
		if (count > 0) {
			resp.setTotalSize(count);
			
			defQuery.orderByProcessDefinitionVersion();
			defQuery.desc();

			List<ProcessDefinition> list = defQuery.listPage(
					getFirstResult(reqBean.getPage(), reqBean.getSize()), getMaxResult(reqBean.getSize()));
			
			if (list != null) {
				resp.setSize(list.size());
				for (int i = 0; i < list.size(); i++) {
					ProcessDefinition def = list.get(i);
					if (def == null) {
						continue;
					}
					QueryDeployedResponce body = new QueryDeployedResponce();
					BeanUtils.copyProperties(def, body, "processDefinition");
					resBodyList.add(body);
				}
			}

		}
		resp.setData(resBodyList);
		return resp;

	}

	@Override
	public void activeProcessDefinition(ActiveProcessDefinitionRequest reqBean) throws WorkflowException {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(reqBean.getProcessDefinitionId())
				.processDefinitionTenantId(reqBean.getSystemCode())
				.singleResult();
		if (processDefinition == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_DEFINITION, reqBean.getProcessDefinitionId());
		}
		try {
			repositoryService.activateProcessDefinitionById(reqBean.getProcessDefinitionId(), 
					reqBean.isActiveProcessInstances(), 
					reqBean.getActivationDate());
		} catch (ActivitiObjectNotFoundException e) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_DEFINITION, reqBean.getProcessDefinitionId());
		} catch (ActivitiException e) {
			throw new WorkflowException(ErrorCode.PROCESS_DEFINITION_HAS_BEEN_ACTIVED, reqBean.getProcessDefinitionId());
		}
	}
	
	@Override
	public void suspendProcessDefinition(SuspendProcessDefinitionRequest reqBean) throws WorkflowException {
		try {
			repositoryService.suspendProcessDefinitionById(reqBean.getProcessDefinitionId(), 
					reqBean.isSuspendProcessInstances(), 
					reqBean.getSuspensionDate());
		} catch (ActivitiObjectNotFoundException e) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_DEFINITION, reqBean.getProcessDefinitionId());
		} catch (ActivitiException e) {
			throw new WorkflowException(ErrorCode.PROCESS_DEFINITION_HAS_BEEN_SUSPENDED, reqBean.getProcessDefinitionId());
		}
	}
	
	@Override
	public void activeProcessInstance(ActiveProcessInstanceRequest reqBean) throws WorkflowException {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(reqBean.getProcessInstanceId())
				.processInstanceTenantId(reqBean.getSystemCode())
				.singleResult();
		if (processInstance == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_INSTANCE, reqBean.getProcessInstanceId());
		}
		try {
			runtimeService.activateProcessInstanceById(reqBean.getProcessInstanceId());
		} catch (ActivitiObjectNotFoundException e) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_INSTANCE, reqBean.getProcessInstanceId());
		} catch (ActivitiException e) {
			throw new WorkflowException(ErrorCode.PROCESS_INSTANCE_HAS_BEEN_ACTIVED, reqBean.getProcessInstanceId());
		}
	}
	
	@Override
	public void suspendProcessInstance(SuspendProcessInstanceRequest reqBean) throws WorkflowException {
		try {
			runtimeService.suspendProcessInstanceById(reqBean.getProcessInstanceId());
		} catch (ActivitiObjectNotFoundException e) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_INSTANCE, reqBean.getProcessInstanceId());
		} catch (ActivitiException e) {
			throw new WorkflowException(ErrorCode.PROCESS_INSTANCE_HAS_BEEN_SUSPENDED, reqBean.getProcessInstanceId());
		}
	}
	
	@Override
	public InputStream getProcessGraphics(GetProcessGraphicsRequest reqBean) throws WorkflowException {
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(reqBean.getProcessInstanceId()).singleResult();
		if (processInstance == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_INSTANCE, reqBean.getProcessInstanceId());
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processInstance.getProcessDefinitionId())
				.singleResult();

		String resourceName;
		InputStream resourceAsStream;
		if (reqBean.isGraph()) {
			resourceName = processDefinition.getDiagramResourceName();
			resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		} else {
			resourceName = processDefinition.getResourceName();
			resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		}
			
        
//        byte[] b = new byte[1024];
//        int len = -1;
//        List<byte[]> byteList = Lists.newArrayList();
//        int length = 0;
//        try {
//			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
//				length += len;
//				if (len == b.length) {
//					byteList.add(b);
//				} else {
//					byte[] tmp = new byte[len];
//					System.arraycopy(b, 0, tmp, 0, tmp.length);
//					byteList.add(tmp);
//				}
//			}
//			byte[] bytes = new byte[length];
//			for (byte[] tmp : byteList) {
//				System.arraycopy(tmp, 0, bytes, 0, tmp.length);
//			}
//			return bytes;
//			
//		} catch (Exception e) {
//			throw new RestException(ErrorCode.SYSTEM_ERROR);
//		}
        return resourceAsStream;
	}

	@Override
	public PageableResponseBean<List<Task>> queryCandidateTask(QueryTaskRequest reqBean) {
		// TODO: 根据登录用户名查询用户所属组
		TaskQuery taskQuery = taskService.createTaskQuery().or()
				.taskCandidateGroupIn(Lists.newArrayList()).taskCandidateUser("")
				.endOr()
				.processDefinitionKey(reqBean.getProcessDefinitionKey()).taskTenantId(reqBean.getSystemCode());
		List<Task> tasks = taskQuery.listPage(getFirstResult(reqBean.getPage(), reqBean.getSize()), getMaxResult(reqBean.getSize()));
		return constructRespBean(taskQuery.count(), tasks);
	}
	
	@Override
	public ProcessInstance queryProcessInstance(Task task) {
		return runtimeService.createProcessInstanceQuery().active().processDefinitionId(task.getProcessDefinitionId()).singleResult();
	}
	
	public PageableResponseBean<List<HistoricProcessInstance>> queryInitiatedProcessInstance(QueryProcessRequest reqBean) {
		// TODO: 根据登录用户名查询
		HistoricProcessInstanceQuery processInstanceQuery = historyService.createHistoricProcessInstanceQuery()
				.processInstanceTenantId(reqBean.getSystemCode())
				.variableValueEquals(Consts.PROCESS_INITIATOR, reqBean.getUser());
		
		if (StringUtils.hasText(reqBean.getProcessDefinitionKey())) {
			processInstanceQuery = processInstanceQuery.processDefinitionKey(reqBean.getProcessDefinitionKey());
		}
		
		List<HistoricProcessInstance> processInstances = processInstanceQuery.listPage(
				getFirstResult(reqBean.getPage(), reqBean.getSize()), getMaxResult(reqBean.getSize()));
		
		return constructRespBean(processInstanceQuery.count(), processInstances);
	}
	
	private <T> PageableResponseBean<List<T>> constructRespBean(long totalSize, List<T> data) {
		PageableResponseBean<List<T>> resp = new PageableResponseBean<>();
		
		if (totalSize > 0) {
			resp.setTotalSize(totalSize);
			resp.setSize(data.size());
			resp.setData(data);
		}
		return resp;
	}
	
	@Override
	public HistoricActivityInstance queryLatestHistoricActivityInstance(String processInstanceId) {
		List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().desc().list();
		if (activityInstances != null && activityInstances.size() > 0) {
			Optional<HistoricActivityInstance> optional = activityInstances.stream().findFirst();
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		
		return null;
	}
	
	@Override
	public PageableResponseBean<List<HistoricTaskInstance>> queryRelatedTasks(QueryTaskRequest reqBean) {
		// TODO: 根据登录用户名查询
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
				.taskTenantId(reqBean.getSystemCode())
//				.taskAssignee("")
				.taskAssignee(reqBean.getUser());
		if (StringUtils.hasText(reqBean.getProcessDefinitionKey())) {
			query = query.processDefinitionKey(reqBean.getProcessDefinitionKey());
		}
		List<HistoricTaskInstance> taskInstances = query.orderByTaskCreateTime().asc().listPage(
				getFirstResult(reqBean.getPage(), reqBean.getSize()),getMaxResult(reqBean.getSize()));
		return constructRespBean(query.count(), taskInstances);
	}
	
	@Override
	public HistoricProcessInstance queryHistoricProcessInstance(String processInstanceId) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}
	
	@Override
	public void claimTask(ClaimTaskRequest req) throws WorkflowException {
		String taskId = req.getTaskId();
		
		Task task = getTask(taskId, req.getSystemCode());
		
		// TODO: 根据登录用户名查询
		taskService.claim(task.getId(), req.getUserId());
	}

	private Task getTask(String taskId, String systemCode) throws WorkflowException {
		TaskQuery createTaskQuery = taskService.createTaskQuery().taskId(taskId).active().taskTenantId(systemCode);
		
		Task task = createTaskQuery.singleResult();
		if (task == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_TASK_RECORD, taskId);
		}
//		if (task.isSuspended()) {
//			throw new WorkflowException(ErrorCode.TASK_HAS_BEEN_SUSPENDED, task.getId());
//		}
		return task;
	}
	
	public void completeTask(CompleteTaskRequest req) throws WorkflowException {
		Task task = getTask(req.getTaskId(), req.getSystemCode());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		if (req.getProcessVariables() != null) {
			variables.putAll(req.getProcessVariables());
		}
		
		taskService.complete(task.getId(), variables);
	}

	@Override
	public List<RejectTaskNode> getRejectTaskNodes(RejectTaskRequest req) throws WorkflowException {
		Task task = taskService.createTaskQuery().taskId(req.getTaskId()).taskTenantId(req.getSystemCode()).singleResult();
		if (task == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_TASK_RECORD, req.getTaskId());
		}
		// TODO:需要判断是否任务支持选择驳回节点
		
		List<RejectTaskNode> rejectTaskNodes = Lists.newArrayList();
		
		// 之前代码没有判断最近的路径，将所有上级节点都返回，有问题
//		List<PvmActivity> pvmActivities = getAllNodeToReject(task);
//		for (PvmActivity pvmActivity : pvmActivities) {
//			RejectTaskNode rejectTaskNode = new RejectTaskNode();
//			rejectTaskNode.setId(pvmActivity.getId());
//			rejectTaskNode.setName((String) pvmActivity.getProperty("name"));
//			rejectTaskNodes.add(rejectTaskNode);
//		}
		
		List<PvmActivity> pvmActivities = getAllValidRouteNodeToReject(task);
		for (PvmActivity pvmActivity : pvmActivities) {
			RejectTaskNode rejectTaskNode = new RejectTaskNode();
			rejectTaskNode.setId(pvmActivity.getId());
			rejectTaskNode.setName((String) pvmActivity.getProperty("name"));
			rejectTaskNodes.add(rejectTaskNode);
		}
		return rejectTaskNodes;
	}
	
	/**
	 * 获取拒绝时可用的节点。
	 * <br><br>
	 * 需要满足如下条件：
	 * <ol>
	 * <li>所有节点都是当前节点之前的节点，
	 * <li>节点包括从起始点到当前节点之前的点，
	 * <li>从历史记录里查询到的从起始节点开始的所有节点时间顺序从小到大排列
	 * </ol>
	 * 
	 * @param task
	 * @return
	 */
	private List<PvmActivity> getAllValidRouteNodeToReject(Task task) {
		PvmActivity activity = getCurrentPvmActivity(task);
		return getAllIncomingValidRoutes(task, getRejectNodeType(), activity);
	}
	
	/**
	 * 递归向前查找之前的流程节点，返回一条合法的路径
	 * @param task
	 * @param nodeTypes 要查找的节点类型
	 * @param beginActivity 起始节点
	 * @return
	 */
	private List<PvmActivity> getAllIncomingValidRoutes(Task task, List<String> nodeTypes,PvmActivity beginActivity) {
		
		List<PvmActivity> validRoutes = Lists.newArrayList();
		
		log.debug("Begin to find incoming transitions for pvmactivity,id:{}, name:{}, type:{}", 
				beginActivity.getId(), beginActivity.getProperty("name"), beginActivity.getProperty("type"));
		
		List<PvmTransition> transitions = beginActivity.getIncomingTransitions();
		if (transitions == null || transitions.isEmpty()) {
			log.debug("This pvmactivity node has no transitions, return");
			return Lists.newArrayList();
		}
		
		List<PvmActivity> activities = transitions.stream().map(transition -> transition.getSource()).collect(Collectors.toList());
		for (PvmActivity pvmActivity : activities) {
			validRoutes.addAll(getAllIncomingValidRoutes(task, nodeTypes, pvmActivity));
			if (nodeTypes.contains(pvmActivity.getProperty("type"))) {
				validRoutes.add(0, pvmActivity);
				
				StringBuilder sBuilder = new StringBuilder();
				for (PvmActivity activtity : validRoutes) {
					sBuilder.append(String.format("id:%s, name:%s -> ", activtity.getId(), activtity.getProperty("name")));
				}
				
				// 判断是否为合法路径，只有一条路径非法，找到后直接退出
				if (isPvmActivitiesIsValidRoute(task, validRoutes)) {
					log.debug("route is valid:" + sBuilder.substring(0, sBuilder.length() - 3));
					break;
				} else {
					log.debug("route is not valid:" + sBuilder.substring(0, sBuilder.length() - 3));
					validRoutes.clear();
				}
			}
		}
		
		return validRoutes;
	}
	
	/**
	 * 判断是否为合法路径。只需判断路径的第一个和第二个节点在历史表中的结束时间先后顺序一致即可确定。其它节点在递归调用时已经判断过了
	 * @param task
	 * @param pvmActivities
	 * @return
	 */
	private boolean isPvmActivitiesIsValidRoute(Task task, List<PvmActivity> pvmActivities) {
		HistoricActivityInstance instance1 = findHistoricActivityInstance(task, pvmActivities.get(0).getId());
		
		// 历史表不存在，非法路径
		if (instance1 == null) {   
			return false;
		} else if (pvmActivities.size() == 1) {  
			
			// 只有一个节点，合法路径
			return true;
		} else {
			HistoricActivityInstance instance2 = findHistoricActivityInstance(task, pvmActivities.get(1).getId());
			
			// 历史表不存在，非法路径
			if (instance2 == null) {
				return false;
			} else if (instance1.getEndTime().before(instance2.getEndTime())) {
				
				// 两个节点时间顺序不对，非法路径
				return false;
			}
		}
		
		return true;
	}
	
	private HistoricActivityInstance findHistoricActivityInstance(Task task, String activityId) {
		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).finished().activityId(activityId)
				.orderByHistoricActivityInstanceEndTime().desc().list();
		if (historicActivityInstances != null && historicActivityInstances.size() != 0) {
			return historicActivityInstances.get(0);
		}
		return null;
	}
	
	
//	private List<PvmActivity> getAllNodeToReject(Task task) {
//		PvmActivity activity = getCurrentPvmActivity(task);
//		List<String> findTypes = getRejectNodeType();
//		
//		return getAllIncomingActivities(activity, findTypes, Sets.newHashSet());
//	}

	private ArrayList<String> getRejectNodeType() {
		return Lists.newArrayList(BpmnXMLConstants.ELEMENT_TASK_USER, BpmnXMLConstants.ELEMENT_EVENT_START);
	}

	/**
	 * 获取当前的活动节点
	 * @param task
	 * @return
	 */
	private PvmActivity getCurrentPvmActivity(Task task) {
		ReadOnlyProcessDefinition deployedProcessDefinition = ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
		return deployedProcessDefinition.findActivity(task.getTaskDefinitionKey());
	}
	
	/**
	 * 获取当前的活动节点
	 * @param task
	 * @return
	 */
	private PvmActivity getCurrentPvmActivity(ProcessInstance processInstance) {
		ReadOnlyProcessDefinition deployedProcessDefinition = ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		return deployedProcessDefinition.findActivity(processInstance.getActivityId());
	}
	
	/**
	 * 获取启动节点
	 * @param task
	 * @return
	 */
	private PvmActivity getStartPvmActivity(String processDefinitionId) {
		ReadOnlyProcessDefinition deployedProcessDefinition = ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
		return deployedProcessDefinition.getActivities().stream()
				.filter(a->a.getProperty("type").equals(BpmnXMLConstants.ELEMENT_EVENT_START)).findAny().get();
	}
	
	/**
	 * 获取结束节点
	 * @param task
	 * @return
	 */
	private PvmActivity getEndPvmActivity(String processDefinitionId) {
		ReadOnlyProcessDefinition deployedProcessDefinition = ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
		return deployedProcessDefinition.getActivities().stream()
				.filter(a->a.getProperty("type").equals(BpmnXMLConstants.ELEMENT_EVENT_END)).findAny().get();
	}
	
	/**
	 * 获取指定节点的直接后续节点
	 * @param startNode
	 * @param nodeType
	 * @return
	 */
	private List<PvmActivity> getOutgoingPvmActivity(PvmActivity startNode, List<String> nodeType) {
		Map<Boolean, List<PvmActivity>> map = startNode.getOutgoingTransitions().stream().map(t->t.getDestination()).collect(Collectors.partitioningBy(a->nodeType.contains(a.getProperty("type"))));
		List<PvmActivity> outgoingPvmActivities = Lists.newArrayList(map.get(Boolean.TRUE));
		for (PvmActivity pvmActivity : map.get(Boolean.FALSE)) {
			outgoingPvmActivities.addAll(getOutgoingPvmActivity(pvmActivity, nodeType));
		}
		return outgoingPvmActivities;
	}
	
	/**
	 * 获取指定节点的直接前置节点
	 * @param startNode
	 * @param nodeType
	 * @return
	 */
	private List<PvmActivity> getIncomingPvmActivity(PvmActivity startNode, List<String> nodeType) {
		Map<Boolean, List<PvmActivity>> map = startNode.getIncomingTransitions().stream().map(t->t.getDestination()).collect(Collectors.partitioningBy(a->nodeType.contains(a.getProperty("type"))));
		List<PvmActivity> incomingPvmActivities = Lists.newArrayList(map.get(Boolean.TRUE));
		for (PvmActivity pvmActivity : map.get(Boolean.FALSE)) {
			incomingPvmActivities.addAll(getIncomingPvmActivity(pvmActivity, nodeType));
		}
		return incomingPvmActivities;
	}
	
//	/**
//	 * 递归向前查找节点
//	 * @param beginActivity 起始节点
//	 * @param findTypes 要查找的节点类型
//	 * @param foundNodes 已经找过的节点，防止重复查找
//	 * @return
//	 */
//	private List<PvmActivity> getAllIncomingActivities(PvmActivity beginActivity, List<String> findTypes, Set<PvmProcessElement> foundNodes) {
//		
//		List<PvmActivity> pvmActivities = Lists.newArrayList();
//		
//		log.debug("Begin to find incoming pvmactivity,id:{}, name:{}, type:{}", beginActivity.getId(), 
//				beginActivity.getProperty("name"), beginActivity.getProperty("type"));
//		
//		// 防止寻找重复对象
//		if (foundNodes.contains(beginActivity)) {
//			log.debug("Pvmactivity node:{} has been found, skip it", beginActivity.getId());
//			return pvmActivities;
//		} 
//		
//		List<PvmTransition> transitions = beginActivity.getIncomingTransitions();
//		if (transitions == null || transitions.isEmpty()) {
//			log.debug("This pvmactivity node has no transitions, return");
//			return pvmActivities;
//		}
//		log.debug("Begin to filter transitions:");
//		transitions.forEach(t->log.debug("id:{}, name:{}, type:{}", t.getId(), t.getProperty("name"), t.getProperty("type")));
//		
//		foundNodes.addAll(transitions);
//		
//		Map<Boolean, List<PvmActivity>> map = transitions.stream().map(transition -> transition.getSource())
//				.collect(Collectors.partitioningBy(tmpActivity -> findTypes.contains(tmpActivity.getProperty("type"))));
//		pvmActivities.addAll(map.get(Boolean.TRUE));
//		map.get(Boolean.TRUE).forEach(t->log.debug("Found pvmactivity node, id:{}, name:{}, type:{}", 
//				t.getId(), t.getProperty("name"), t.getProperty("type")));
//		
//		for (List<PvmActivity> pvmActivity : map.values()) {
//			for (PvmActivity pvmActivity2 : pvmActivity) {
//				pvmActivities.addAll(getAllIncomingActivities(pvmActivity2, findTypes, foundNodes));
//				foundNodes.add(pvmActivity2);
//			}
//		}
//		
//		return pvmActivities;
//	}
	
	@Override
	public void rejectTaskToPreviousNode(RejectTaskRequest req) throws WorkflowException {
		Task task = taskService.createTaskQuery().taskId(req.getTaskId()).taskTenantId(req.getSystemCode()).singleResult();
		
		if (task == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_TASK_RECORD, req.getTaskId());
		}
		// TODO:需要判断是否任务支持选择驳回节点
		List<PvmActivity> routeNodeToReject = getAllValidRouteNodeToReject(task);
		
		completeTaskToNode(task, (ActivityImpl) routeNodeToReject.get(0), req.getProcessVariables());
	}
	
	@Override
	public void rejectTask(RejectTaskRequest req) throws WorkflowException {
		Task task = taskService.createTaskQuery().taskId(req.getTaskId()).taskTenantId(req.getSystemCode()).singleResult();
		
		if (task == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_TASK_RECORD, req.getTaskId());
		}
		// TODO:需要判断是否任务支持选择驳回节点
		List<PvmActivity> allNodeToReject = getAllValidRouteNodeToReject(task);
		Optional<PvmActivity> any = allNodeToReject.stream().filter(node -> node.getId().equals(req.getRejectNodeId())).findAny();
		if (!any.isPresent()) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_TASK_REJECT_NODE, req.getRejectNodeId());
		}
		
		ActivityImpl pvmActivity = (ActivityImpl) any.get();
		
		completeTaskToNode(task, pvmActivity, req.getProcessVariables());
		
	}
	
	private void completeTaskToNode(Task task, ActivityImpl destinationNode, Map<String, String> processVariables) {
		ActivityImpl currentPvmActivity = (ActivityImpl) getCurrentPvmActivity(task);
		
		// 保存当前节点的输出流程
		List<PvmTransition> originPvmTransitions = Lists.newArrayList();
		originPvmTransitions.addAll(currentPvmActivity.getOutgoingTransitions());
		
		// 清空当前输出流程
		currentPvmActivity.getOutgoingTransitions().clear();
		
		// 创建新流程
		TransitionImpl newTransition = currentPvmActivity.createOutgoingTransition();
		newTransition.setDestination(destinationNode);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		if (processVariables != null) {
			variables.putAll(processVariables);
		}
		
		taskService.complete(task.getId(), variables);
		
		// 还原之前的流程
		currentPvmActivity.getOutgoingTransitions().clear();
		currentPvmActivity.getOutgoingTransitions().addAll(originPvmTransitions);
	}
	
	@Override
	public PageableResponseBean<List<HistoricTaskInstance>> queryHistoryAuditRecords(HistoryAuditRecordRequest req) {
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
			.taskTenantId(req.getSystemCode()).processInstanceId(req.getProcessInstanceId())
			.finished().orderByHistoricTaskInstanceEndTime().asc();
		
		List<HistoricTaskInstance> taskInstances = query.listPage(getFirstResult(req.getPage(), req.getSize()), getMaxResult(req.getSize()));
		
		return constructRespBean(query.count(), taskInstances);
	}
	
	@Override
	public List<HistoricVariableUpdate> queryHistoricTaskVariableInstances(String processInstanceId, String taskId) {
		Optional<HistoricActivityInstance> optional = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).list().stream().filter(i->taskId.equals(i.getTaskId())).findAny();
		if (optional.isPresent()) {
			return historyService.createHistoricDetailQuery().activityInstanceId(optional.get().getId()).list()
					.stream().map(t->(HistoricVariableUpdate)t).collect(Collectors.toList());
		}
		
		return Lists.newArrayList();
	}
	
	@Override
	public List<HistoricVariableUpdate> queryHistoricProcessVariableInstance(String processInstanceId) {
		HistoricActivityInstance activityInstance = historyService.createHistoricActivityInstanceQuery()
				.activityType(BpmnXMLConstants.ELEMENT_EVENT_START).processInstanceId(processInstanceId).singleResult();
		if (activityInstance != null) {
			return historyService.createHistoricDetailQuery().activityInstanceId(activityInstance.getId()).list()
					.stream().map(t->(HistoricVariableUpdate)t).collect(Collectors.toList());
		} 
		
		return Lists.newArrayList();
	}
	
	@Override
	public boolean canWithdrawTask(WithdrawTaskRequest req) throws WorkflowException {
//		Task task = taskService.createTaskQuery().taskId(req.getTaskId()).singleResult();
//		if (task == null) {
//			throw new WorkflowException(ErrorCode.CANNOT_FIND_TASK_RECORD, req.getTaskId());
//		}
//		
//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
//		
//		Optional<WfTaskwithdrawSetting> optional = taskwithdrawSettingRepository.findByProcessDefinitionId(processDefinition.getId());
//		if (!optional.isPresent()) {
//			throw new WorkflowException(ErrorCode.TASK_NOT_SUPPORT_WITHDRAW, req.getTaskId());
//		}
//		
//		WfTaskwithdrawSetting taskwithdrawSetting = optional.get();
//		if (taskwithdrawSetting.getWithdraw() == Consts.TaskWithdrawSupport.NO.getValue()) {
//			throw new WorkflowException(ErrorCode.TASK_NOT_SUPPORT_WITHDRAW, req.getTaskId());
//		} else if (Consts.TaskWithdrawType.FIRST_NODE.equals(taskwithdrawSetting.getWithdrawType())) {
//			PvmActivity currentPvmActivity = getCurrentPvmActivity(task);
//			
//			// 前一个用户任务节点
//			List<PvmActivity> incomingPvmActivities = getIncomingPvmActivity(currentPvmActivity, Lists.newArrayList(BpmnXMLConstants.ELEMENT_TASK_USER));
//			
//			
//			
//			// 都没有匹配到当前节点
//			if (!incomingPvmActivities.stream().anyMatch(a->a.equals(currentPvmActivity))) {
//				throw new WorkflowException(ErrorCode.PROCESS_CANNOT_WITHDRAW, req.getTaskId());
//			} 
//		}
		return false;
	}
	
	@Override
	public void withdrawTask(WithdrawTaskRequest req) throws WorkflowException {
		if (!canWithdrawTask(req)) {
			return;
		}
		
//		Task task = taskService.createTaskQuery().taskId(req.getTaskId()).taskTenantId(req.getSystemCode()).singleResult();
//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
//		
//		Optional<WfTaskwithdrawSetting> optional = taskwithdrawSettingRepository.findByProcessDefinitionId(processDefinition.getId());
//		
//		ActivityImpl withdrawNode = new ActivityImpl(Consts.WITHDRAW_NODE_ID, (ProcessDefinitionImpl) processDefinition);
//		withdrawNode.setProperty("name", optional.get().getNodeName());
//		
//		completeTaskToNode(task, withdrawNode, req.getProcessVariables());
	}
	
	@Override
	public boolean canCancelProcessInstance(CancelProcessRequest req) throws WorkflowException {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(req.getProcessInstanceId()).singleResult();
		if (processInstance == null) {
			throw new WorkflowException(ErrorCode.CANNOT_FIND_PROCESS_INSTANCE, req.getProcessInstanceId());
		} 
		
		// TODO: 需要修改为传入登录用户名
		// 用户无法撤销非自己创建的流程
		Optional<HistoricVariableUpdate> variableOptional = queryHistoricProcessVariableInstance(processInstance.getId()).stream().filter(u->u.getVariableName().equals(Consts.PROCESS_INITIATOR)).findFirst();
		if (!variableOptional.isPresent() || !variableOptional.get().getValue().equals(req.getUser())) {
			throw new WorkflowException(ErrorCode.PROCESS_CANNOT_CANCEL_BY_CURRENT_USER, req.getUser());
		}
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
		
		Optional<WfTaskwithdrawSetting> optional = taskwithdrawSettingRepository.findByProcessDefinitionId(processDefinition.getId());
		if (!optional.isPresent()) {
			throw new WorkflowException(ErrorCode.PROCESS_NOT_SUPPORT_CANCEL, req.getProcessInstanceId());
		}
		
		WfTaskwithdrawSetting taskwithdrawSetting = optional.get();
		if (taskwithdrawSetting.getWithdraw() == Consts.TaskWithdrawSupport.NO.getValue()) {
			throw new WorkflowException(ErrorCode.PROCESS_NOT_SUPPORT_CANCEL, req.getProcessInstanceId());
		} else if (Consts.TaskWithdrawType.FIRST_NODE.toString().equals(taskwithdrawSetting.getWithdrawType())) {
			PvmActivity currentPvmActivity = getCurrentPvmActivity(processInstance);
			
			PvmActivity startPvmActivity = getStartPvmActivity(processInstance.getProcessDefinitionId());
			
			// 第一个用户任务节点
			List<PvmActivity> outgoingPvmActivities = getOutgoingPvmActivity(startPvmActivity, Lists.newArrayList(BpmnXMLConstants.ELEMENT_TASK_USER));
			
			// 都没有匹配到当前节点
			if (!outgoingPvmActivities.stream().anyMatch(a->a.equals(currentPvmActivity))) {
				throw new WorkflowException(ErrorCode.PROCESS_CANNOT_CANCEL, req.getProcessInstanceId());
			} 
		}
		return true;
	}
	
	@Override
	public void cancelProcessInstance(CancelProcessRequest req) throws WorkflowException {
		if (!canCancelProcessInstance(req)) {
			return;
		}
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(req.getProcessInstanceId()).singleResult();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
		
		Optional<WfTaskwithdrawSetting> optional = taskwithdrawSettingRepository.findByProcessDefinitionId(processDefinition.getId());
		
		ActivityImpl withdrawNode = (ActivityImpl) getEndPvmActivity(processDefinition.getId());
		withdrawNode.setProperty("name", optional.get().getNodeName());
		
		Task task = taskService.createTaskQuery().processInstanceId(req.getProcessInstanceId()).singleResult();
		completeTaskToNode(task, withdrawNode, req.getProcessVariables());
	}
}
