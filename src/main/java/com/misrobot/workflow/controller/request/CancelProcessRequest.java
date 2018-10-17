package com.misrobot.workflow.controller.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CancelProcessRequest extends RequestJsonBean {
	@ApiModelProperty("流程实例id")
	@NotNull
	private String processInstanceId;
	
	@ApiModelProperty("流程变量，根据具体流程定义传递")
	private java.util.Map<String,String> processVariables;
	
	private String user;
}
