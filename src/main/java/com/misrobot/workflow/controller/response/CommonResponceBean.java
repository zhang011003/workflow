package com.misrobot.workflow.controller.response;

import com.misrobot.workflow.exception.ErrorCode;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class CommonResponceBean {
	@ApiParam("错误码")
	private String errCode = ErrorCode.SUCCESS.getCode();
	@ApiParam("错误描述")
	private String errDesc = ErrorCode.SUCCESS.getMessage();
	@ApiParam("返回数据")
	private Object data;
 
	
}
