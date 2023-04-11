package com.marondalgram.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
	
	// 로그
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 세션 확인	=> 있으면 로그인 상태
		HttpSession session = request.getSession();
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// URI - url path 확인
		String uri = request.getRequestURI();
		logger.info("######## uri: " + uri);
		
		// 비로그인 && /timeline/...	=> 로그인 페이지로 리다이렉트
		 if (userLoginId == null && uri.startsWith("/timeline")) {
			 response.sendRedirect("/user/sign_in_view");
			 return false;
		 }
		 
		// 로그인 && /user/...	=> 포스트 페이지로 리다이렉트
		if (userLoginId != null && uri.startsWith("/user")) {
			response.sendRedirect("/timeline/timeline_list_view");
			return false;
		}

		return true;
	}
}
