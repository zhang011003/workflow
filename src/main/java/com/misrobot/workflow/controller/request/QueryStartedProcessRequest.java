package com.misrobot.workflow.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryStartedProcessRequest extends PageableBean {
	@ApiModelProperty("流程定义id")
	private String processDefinitionId;
	@ApiModelProperty("流程实例id")
	private String processInstanceId;
	@ApiModelProperty("业务主键")
	private String businessKey;
}
