package com.misrobot.workflow.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ActivitiBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof SpringProcessEngineConfiguration) {
			// 修改activiti图形字体
			SpringProcessEngineConfiguration conf = (SpringProcessEngineConfiguration) bean;
			conf.setActivityFontName("宋体");
			conf.setLabelFontName("宋体");
			bean = conf;
		}
		return bean;
	}
}
