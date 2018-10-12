package com.misrobot.workflow.controller.response;

import java.util.Date;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryTaskResponce  {
	@ApiModelProperty("任务id")
	private String id;
	
	@ApiModelProperty("委派人员")
	private String assignee;
	
	@ApiModelProperty("任务名称")
	private String name;
	
	@ApiModelProperty("流程定义key")
	private String processDefinitionKey;
	
	@ApiModelProperty("流程定义名称")
	private String processDefinitionName;
	
	@ApiModelProperty("流程实例id")
	private String processInstanceId;
	
	private Date startTime;
	
	@ApiModelProperty("任务变量")
	private Map<String,Object> taskVariables;
	
	@ApiModelProperty("流程启动变量")
	private Map<String,Object> processVariables;
	
	@ApiModelProperty("业务主键")
	private String businessKey;
	
	@ApiModelProperty("任务是否完成")
	private boolean taskFinished;
	
	@ApiModelProperty("流程是否完成")
	private boolean processFinished;
	
	@ApiModelProperty("流程发起人")
	private String processInitiator;
}
