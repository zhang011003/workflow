package com.misrobot.workflow.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryTaskRequest extends PageableBean {
	@ApiModelProperty("流程定义key")
	private String processDefinitionKey;
	
	private String user;
}
