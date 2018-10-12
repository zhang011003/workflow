package com.misrobot.workflow.controller.response;

import java.util.Date;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HistoryAuditRecordRespose {
	@ApiModelProperty("审核开始日期")
	private Date startTime;
	@ApiModelProperty("审核结束日期")
	private Date endTime;
	@ApiModelProperty("审核人")
	private String assignee;
	@ApiModelProperty("节点名称")
	private String name;
	@ApiModelProperty("任务变量信息")
	private Map<String, Object> taskVariables;
	@ApiModelProperty("流程变量信息")
	private Map<String, Object> processVariables;
}
