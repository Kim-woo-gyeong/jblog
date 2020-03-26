package com.douzone.jblog.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileUploadConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(52428800);
		multipartResolver.setMaxInMemorySize(4096);
		multipartResolver.setDefaultEncoding("UTF-8");
		
		return multipartResolver;
	}
	
	//MVC Resources(URL Magic Mappling)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/upimages/**").addResourceLocations("file:/jblog-uploads/");
	}
}
