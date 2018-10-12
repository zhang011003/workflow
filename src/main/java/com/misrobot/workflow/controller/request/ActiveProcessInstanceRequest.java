package com.misrobot.workflow.controller.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ActiveProcessInstanceRequest extends RequestJsonBean {
	@ApiModelProperty("流程定义id")
	@NotNull
	private String processInstanceId;
}