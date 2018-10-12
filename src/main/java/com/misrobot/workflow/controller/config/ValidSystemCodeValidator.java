package com.misrobot.workflow.controller.config;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.misrobot.workflow.config.ValidSystemCode;
import com.misrobot.workflow.model.WfSystemInfo;
import com.misrobot.workflow.service.SystemInfoService;

public class ValidSystemCodeValidator implements ConstraintValidator<ValidSystemCode, String> {

	@Autowired
	private SystemInfoService systemInfoService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		Optional<WfSystemInfo> systemCode = systemInfoService.getSystemInfoBySystemCode(value);
		return systemCode.isPresent() && systemCode.get().isEnabled();
	}

}
