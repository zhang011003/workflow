package com.misrobot.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({"org.activiti.rest.diagram", "org.activiti.editor", "com.misrobot.workflow"})
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
//@EnableAsync
@EnableDiscoveryClient
public class WorkflowApplication implements WebMvcConfigurer {
	
	public static void main(String[] args) {
		SpringApplication.run(WorkflowApplication.class, args);
	}
	
//	// activiti使用jackson解析字符串，如果换成fastjson，需要将fastjson放在jackson转换器之前，并且不需要解析返回的ObjectNode对象
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		WebMvcConfigurer.super.configureMessageConverters(converters);
//		
//		//1.定义一个convert转换消息对象
//        FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter() {
//        	@Override
//        	protected boolean supports(Class<?> clazz) {
//        		// 过滤ObjectNode类，否则activiti编辑器图形无法正常展示
//        		return clazz != ObjectNode.class;
//        	}
//        };
//        fastConverter.setSupportedMediaTypes(Lists.newArrayList(
//        		MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));
//        
//        //2.添加fastjson的配置信息，比如：是否要格式化返回json数据
//        FastJsonConfig fastJsonConfig=new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat	
//        );
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        
//        //将fastConverter添加到jackson转换器之前，否则activiti编辑器的图形展示不出来
//        boolean hasJacksonConverter = false;
//        for (int i = 0; i < converters.size(); i++) {
//        	HttpMessageConverter<?> converter = converters.get(i);
//        	if (converter instanceof AbstractJackson2HttpMessageConverter) {
//        		converters.add(i, fastConverter);
//        		hasJacksonConverter = true;
//        		break;
//        	}
//		}
//        
//        if (!hasJacksonConverter) {
//        	converters.add(converters.size(), fastConverter);
//		}
//	}
	
//	@Bean
//	public JsonpCallbackFilter filter(){
//		return new JsonpCallbackFilter();
//	}
}
