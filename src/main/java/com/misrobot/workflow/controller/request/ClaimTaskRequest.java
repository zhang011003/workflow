package com.misrobot.workflow.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ClaimTaskRequest extends RequestJsonBean {

	@ApiModelProperty("任务id")
	private String taskId;
	private String userId;
}
