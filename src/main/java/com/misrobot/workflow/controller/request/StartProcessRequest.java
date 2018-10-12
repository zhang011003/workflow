package com.misrobot.workflow.controller.request;

import java.util.Map;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StartProcessRequest extends RequestJsonBean {
	@ApiModelProperty("流程定义key")
	@NotNull
	private String processDefinitionKey;
	@ApiModelProperty("业务主键，供业务后续使用")
	private String businessKey;
	@ApiModelProperty("流程变量，根据具体流程定义传递")
	private Map<String,String> processVariables;

}
