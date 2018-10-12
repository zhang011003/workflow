package com.misrobot.workflow.controller.response;

import lombok.Data;

@Data
public class VariableResponse {
	private String name;
	private String type;
	private Object value;
}
