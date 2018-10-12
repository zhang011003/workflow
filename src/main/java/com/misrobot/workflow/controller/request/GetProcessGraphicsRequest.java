package com.misrobot.workflow.controller.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetProcessGraphicsRequest extends RequestJsonBean {
	@ApiModelProperty("流程实例id")
	@NotNull
	private String processInstanceId;
	
	private boolean graph = true;
}
