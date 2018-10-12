package com.misrobot.workflow.controller.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StartProcessResponce  {
	@ApiModelProperty("流程实例id")
	private String processInstanceId;
}
