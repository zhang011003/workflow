package com.misrobot.workflow.controller.response;

import java.util.Date;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryProcessResponce {
	@ApiModelProperty("流程实例id")
	private String processInstanceId;
	@ApiModelProperty("流程开始时间")
	private Date startTime;
	@ApiModelProperty("流程结束时间")
	private Date endTime;
	@ApiModelProperty("业务主键")
	private String businessKey;
	@ApiModelProperty("流程定义key")
	private String processDefinitionKey;
	@ApiModelProperty("流程定义名称")
	private String processDefinitionName;
	@ApiModelProperty("流程是否结束")
	private boolean processFinished;
	@ApiModelProperty("流程变量")
	private Map<String, Object> processVariables;
	@ApiModelProperty("当前活动名称")
	private String currentActivityName;
//	@ApiModelProperty("任务id")
//	private String taskId;
}
