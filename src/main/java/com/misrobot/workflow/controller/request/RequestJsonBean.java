package com.misrobot.workflow.controller.request;

import javax.validation.constraints.NotNull;

import com.misrobot.workflow.config.ValidSystemCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestJsonBean {
	
//	private Object body;
	
//	protected String command;
//	protected String sessionid;
//	protected String loginid;
//	protected String jsonSign;
//	protected String jsonSignTime;
	
	@ApiModelProperty("系统编码")
	@NotNull
	@ValidSystemCode
	private String systemCode;
//	
//	public String getCommand() {
//		return command;
//	}
//
//	public void setCommand(String command) {
//		this.command = command;
//	}
//
//	public String getSessionid() {
//		return sessionid;
//	}
//
//	public void setSessionid(String sessionid) {
//		this.sessionid = sessionid;
//	}
//
//	public String getLoginid() {
//		return loginid;
//	}
//
//	public void setLoginid(String loginid) {
//		this.loginid = loginid;
//	}

	// private String areaCode;
	//
	// public String getAreaCode() {
	// return areaCode;
	// }

//	public String getJsonSignTime() {
//		return jsonSignTime;
//	}
//
//	public void setJsonSign(String jsonSign) {
//		this.jsonSign = jsonSign;
//	}
//
//	public void setJsonSignTime(String jsonSignTime) {
//		this.jsonSignTime = jsonSignTime;
//	}
//
//	public Object getBody() {
//		return body;
//	}
//
//	public void setBody(Object body) {
//		this.body = body;
//	}
//
//	public String getJsonSign() {
//		return jsonSign;
//	}
//
//	public void setSessionData(SessionData sd) {
//		if (sd != null) {
//			if (sessionid == null || sessionid.trim().length() == 0) {
//				sessionid = sd.getSessionid();
//			}
//			if (loginid == null || loginid.trim().length() == 0) {
//				loginid = sd.getLoginid();
//			}
//		}
//
//	}
}
