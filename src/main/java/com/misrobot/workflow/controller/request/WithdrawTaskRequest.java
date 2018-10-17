package com.misrobot.workflow.controller.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class WithdrawTaskRequest extends RequestJsonBean {
	@ApiModelProperty("任务id")
	@NotNull
	private String taskId;
	
	@ApiModelProperty("任务变量，根据具体任务传递")
	private java.util.Map<String,String> taskVariables;
}
