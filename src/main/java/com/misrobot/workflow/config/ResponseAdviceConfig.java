package com.misrobot.workflow.config;

import java.util.List;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.google.common.collect.Lists;
import com.misrobot.workflow.controller.response.CommonResponceBean;

@RestControllerAdvice
public class ResponseAdviceConfig implements ResponseBodyAdvice<Object> {

	/**
	 * 格式需要统一的请求地址
	 */
	private List<String> uniformRequests = Lists.newArrayList("/model", "/process");
	private List<MediaType> uniformRequestContentType = Lists.newArrayList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8);
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (uniformRequestContentType.stream().anyMatch(mediaType-> mediaType.equals(selectedContentType))
				&& request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
			String requestURI = servletServerHttpRequest.getServletRequest().getRequestURI();
			String contextPath = servletServerHttpRequest.getServletRequest().getContextPath();
			String substringURL = requestURI.substring(contextPath.length());
			Optional<String> optional = uniformRequests.stream()
					.filter(uniformRequest->substringURL.startsWith(uniformRequest)).findAny();
			if (optional.isPresent()) {
				CommonResponceBean resp = new CommonResponceBean();
				if (body instanceof List) {
					List<?> list = (List<?>)body;
					resp.setCount(list.size());
					resp.setData(body);
				} else if (body != null) {
					if (!(body instanceof CommonResponceBean)) {
						resp.setData(body);
					} else {
						resp = (CommonResponceBean)body;
					}
				}
				body = resp;
			}
		} 
		return body;
	}

}