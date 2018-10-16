package com.misrobot.workflow.controller.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageableResponseBean<T> {
	@ApiModelProperty("当前页条数")
	private int size;
	@ApiModelProperty("总条数")
	private long totalSize;
	@ApiModelProperty("当前页数据")
	private T data;
}
