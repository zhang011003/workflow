package com.misrobot.workflow.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NewModelRequest extends RequestJsonBean {
	private String name;
}
