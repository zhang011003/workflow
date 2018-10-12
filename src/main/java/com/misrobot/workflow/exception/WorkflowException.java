package com.misrobot.workflow.exception;

/**
 * @author zhang
 */
public class WorkflowException extends Exception {
	private static final long serialVersionUID = 2593748449349333216L;
	
	private ErrorCode errorCode;
	
	public WorkflowException(ErrorCode errorCode, String... args) {
		super(errorCode.getMessage(args));
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
