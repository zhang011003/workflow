package com.misrobot.workflow.controller.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RejectTaskRequest extends RequestJsonBean {
	@ApiModelProperty("任务id")
	@NotNull
	private String taskId;
	
	@ApiModelProperty("驳回的节点id")
	private String rejectNodeId;
	
	@ApiModelProperty("流程变量，根据具体流程定义传递")
	private java.util.Map<String,String> processVariables;
}
