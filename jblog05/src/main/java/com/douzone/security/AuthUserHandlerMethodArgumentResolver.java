package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.jblog.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		System.err.println("User:"+authUser);
		//@AuthUser가 안 붙어 있으면 (Type까지 맞아야 함)
		if(authUser == null) {
			return false;
		}
		
		//Type 검사 진행 ParameterType = UserVo
		if(!parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		
		//@AuthUser 가 붙어있고 파라미터 타입이 UserVo
		return true;
	}

	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		if(!supportsParameter(parameter)) {
			//null을 return
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		System.err.println("session:"+session);
		if(session == null) {
			
			return "redirect:/user/login";
		}
		
		return session.getAttribute("authUser");
	}

}
