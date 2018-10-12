package com.misrobot.workflow.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageableBean extends RequestJsonBean {
	@ApiModelProperty("页数")
	private Integer page;
	@ApiModelProperty("每页条数")
	private Integer size;
}
