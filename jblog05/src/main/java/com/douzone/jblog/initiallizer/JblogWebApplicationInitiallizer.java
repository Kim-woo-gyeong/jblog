package com.douzone.jblog.initiallizer;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.douzone.jblog.config.AppConfig;
import com.douzone.jblog.config.WebConfig;

public class JblogWebApplicationInitiallizer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		// Servlet 필터를 만들어서 return
		return new Filter[] {new CharacterEncodingFilter("UTF-8", true)};
	}

	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		// create DispacherServlet
		// 부모가 만들어서 return?
		DispatcherServlet disPactcherServlet = (DispatcherServlet)super.createDispatcherServlet(servletAppContext);
		
		//Global Exception(Exception Handler)가 없으면 error. tomcat으로 올리지말고 throw 해라.
		disPactcherServlet.setThrowExceptionIfNoHandlerFound(true);
		
		return disPactcherServlet;
	}
	
	

}
