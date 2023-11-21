package com.example.ex8.security.filter;

import com.example.ex8.security.dto.AuthMembersDTO;
import com.example.ex8.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

@Log
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
	private JWTUtil jwtUtil;

	//생성자와 메서드 재정의가 필수
	public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
		super(defaultFilterProcessesUrl);
		this.jwtUtil=jwtUtil;
	}

	// 사용자 인증
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		log.info("Api 로그인 필터+++++++++++++++++++");
		String email=request.getParameter("email");
		String pw=request.getParameter("pw");
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,pw);
		log.info("토큰 : "+authToken);
		return getAuthenticationManager().authenticate(authToken);
//		if (email==null){
//			throw new BadCredentialsException("email cannot be null");
//		}
//		return null;
	}

	//사용자 인증이 성공적으로되었을 경우 토큰 생성
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		log.info("로그인필터...성공 : "+authResult);
		log.info("로그인필터 성공 결과 : "+authResult.getPrincipal());

		String email=((AuthMembersDTO)authResult.getPrincipal()).getUsername();

		String tocken=null;
		try{
			tocken=jwtUtil.generateToken(email);
			response.setContentType("text/plain");
			response.getOutputStream().write(tocken.getBytes());
			log.info(tocken);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
