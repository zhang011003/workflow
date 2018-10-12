package com.misrobot.workflow.controller.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryDeployedResponce {
//	@ApiModelProperty("流程定义id")
//	private String processDefinitionId;
	@ApiModelProperty("流程定义名称")
	private String processDefinitionName;
	@ApiModelProperty("流程定义版本")
	private int processDefinitionVersion;
	@ApiModelProperty("流程定义key")
	private String processDefinitionKey;
	@ApiModelProperty("流程是否被挂起")
	private boolean suspended;
}
