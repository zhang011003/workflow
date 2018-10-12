package com.misrobot.workflow.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.misrobot.workflow.config.ValidSystemCode.List;
import com.misrobot.workflow.controller.config.ValidSystemCodeValidator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;

@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = { ValidSystemCodeValidator.class })
public @interface ValidSystemCode {
	
	String message() default "SystemCodeNotValid";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	@Target({PARAMETER, FIELD})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ValidSystemCode[] value();
	}
}
