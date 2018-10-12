package com.misrobot.workflow.controller.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteModelRequest extends RequestJsonBean {
	
	@ApiModelProperty("模型id")
	@NotNull
	private String modelId;
}
