package org.zerock.ex7.security.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

@Log
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

	//생성자와 메서드 재정의가 필수
	public ApiLoginFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		log.info("Api Login Filter+++++++++++++++++++");
		String email=request.getParameter("email");
		String pw="1";
		if (email==null){
			throw new BadCredentialsException("email cannot be null");
		}
		return null;
	}


}
