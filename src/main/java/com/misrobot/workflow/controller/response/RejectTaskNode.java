package com.misrobot.workflow.controller.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RejectTaskNode  {
	@ApiModelProperty("驳回节点id")
	private String id;
	@ApiModelProperty("驳回节点名称")
	private String name;
}
