package com.hosptialsys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hosptialsys.interceptor.LoginInterceptor;


/**
 * @fuction ：拦截器配置类
 * @author  ：NONWORD
 * @version ：2019年12月6日 下午2:46:37
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/user/login/*/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:/c:/projects/images/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
