package com.misrobot.workflow.controller.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SuspendProcessDefinitionRequest extends RequestJsonBean {
	@ApiModelProperty("流程定义id")
	@NotNull
	private String processDefinitionId;
	@ApiModelProperty("是否挂起流程实例")
	private boolean suspendProcessInstances;
	@ApiModelProperty("挂起日期，如果为空，则立即挂起")
	private Date suspensionDate;
	
}