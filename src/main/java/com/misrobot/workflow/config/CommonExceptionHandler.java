package com.misrobot.workflow.config;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.misrobot.workflow.controller.response.CommonResponceBean;
import com.misrobot.workflow.exception.ErrorCode;
import com.misrobot.workflow.exception.WorkflowException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommonExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(WorkflowException.class)
	public CommonResponceBean handleRestException(WorkflowException e) {
		log.error(e.getMessage(), e);
		CommonResponceBean resp = new CommonResponceBean();
		resp.setErrCode(e.getErrorCode().getCode());
		resp.setErrDesc(e.getMessage());
		return resp;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public CommonResponceBean handleRuntimeException(RuntimeException e) {
		log.error(e.getMessage(), e);
		CommonResponceBean resp = new CommonResponceBean();
		resp.setErrCode(ErrorCode.SYSTEM_ERROR.getCode());
		resp.setErrDesc(ErrorCode.SYSTEM_ERROR.getMessage());
		return resp;
	}
	
	@ExceptionHandler(ActivitiTaskAlreadyClaimedException.class)
	public CommonResponceBean handleActivitiTaskAlreadyClaimedException(ActivitiTaskAlreadyClaimedException e) {
		log.error(e.getMessage(), e);
		CommonResponceBean resp = new CommonResponceBean();
		resp.setErrCode(ErrorCode.TASK_HAS_BEEN_CLAIMED.getCode());
		resp.setErrDesc(ErrorCode.TASK_HAS_BEEN_CLAIMED.getMessage(e.getTaskId()));
		return resp;
	}
	
	@ExceptionHandler(BindException.class)
	public CommonResponceBean handleValidationException(BindException e) {
		log.error(e.getMessage(), e);
		CommonResponceBean resp = new CommonResponceBean();
		resp.setErrCode(ErrorCode.PARAM_ERROR.getCode());
		FieldError fieldError = e.getFieldError();
		if (fieldError != null) {
			String errDesc;
			try {
				errDesc = messageSource.getMessage(fieldError.getDefaultMessage(), null, LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException nsme) {
				errDesc = fieldError.getField() + fieldError.getDefaultMessage();
			}
			resp.setErrDesc(errDesc);
		} else if (e.getGlobalError() != null){
			String defaultMessage = e.getGlobalError().getDefaultMessage();
			try {
				defaultMessage = messageSource.getMessage(defaultMessage, null, LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException nsme) {
				log.error(nsme.getMessage());
			}
			resp.setErrDesc(defaultMessage);
		}
		return resp;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public CommonResponceBean handleValidationException(MethodArgumentNotValidException e) {
		log.error(e.getMessage(), e);
		CommonResponceBean resp = new CommonResponceBean();
		resp.setErrCode(ErrorCode.PARAM_ERROR.getCode());
		FieldError fieldError = null;
		
		BindingResult bindingResult = e.getBindingResult();
		if (bindingResult != null) {
			fieldError = bindingResult.getFieldError();
		}
		if (fieldError != null) {
			String errDesc;
			try {
				errDesc = messageSource.getMessage(fieldError.getDefaultMessage(), null, LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException nsme) {
				errDesc = fieldError.getField() + fieldError.getDefaultMessage();
			}
			resp.setErrDesc(errDesc);
		}
		return resp;
	}
}