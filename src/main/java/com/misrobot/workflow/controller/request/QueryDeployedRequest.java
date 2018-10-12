package com.misrobot.workflow.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryDeployedRequest extends PageableBean {
	@ApiModelProperty("是否只显示最新版本数据")
	private boolean onlyShowLatestedVersionData = true;
}
