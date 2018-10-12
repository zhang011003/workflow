package com.misrobot.workflow.controller.response;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryStartedProcessResponce  {
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("流程实例id")
	private String processInstanceId;
	@ApiModelProperty("活动id")
	private String activityId;
	@ApiModelProperty("业务主键")
	private String businessKey;
	@ApiModelProperty("流程定义id")
	private String processDefinitionId;
	@ApiModelProperty("流程变量")
	private Map<String, Object> processVariables;
}
