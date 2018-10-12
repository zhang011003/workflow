package com.misrobot.workflow.controller.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ActiveProcessDefinitionRequest extends RequestJsonBean {
	@ApiModelProperty("流程定义id")
	@NotNull
	private String processDefinitionId;
	
	@ApiModelProperty("是否激活流程实例")
	private boolean activeProcessInstances;
	
	@ApiModelProperty("激活日期，如果为空，则立即激活")
	private Date activationDate;
}