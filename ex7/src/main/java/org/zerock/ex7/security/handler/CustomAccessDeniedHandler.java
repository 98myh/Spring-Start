package org.zerock.ex7.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Log4j2
public class CustomAccessDeniedHandler implements AccessDeniedHandler, AuthenticationFailureHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response
			, AccessDeniedException accessDeniedException) throws IOException {
		log.info(">>>>>>>>>>>> accessDenied"+request.getContextPath() + "/auth/accessDenied");
//    Authentication auth
//        = SecurityContextHolder.getContext().getAuthentication();
//    if (auth != null) {
//      log.warn("User: " + auth.getName()
//          + " attempted to access the protected URL: "
//          + request.getRequestURI());
//    }
		response.sendRedirect(request.getContextPath() + "/auth/accessDenied");
	}

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		log.info(">>" + request.getContextPath() + "/accessDenied2");
		response.sendRedirect(request.getContextPath() + "/auth/accessDenied");
	}
}