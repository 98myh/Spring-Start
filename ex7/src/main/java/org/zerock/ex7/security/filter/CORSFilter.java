package org.zerock.ex7.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//CORS - Cross Origin Resource Sharing : 웹 페이지가 다른 도메인의 자원에 접근할 수 있도록 허용하는 보안 기능
@Component //해당 클래스를 스프링 애플리케이션 컨텍스트의 빈으로 자동 등록
@Order(Ordered.HIGHEST_PRECEDENCE) //필터의 우선순위가 높다를 표시
public class CORSFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Access-Control-Allow-Methods","*");
		response.setHeader("Access-Control-Allow-Max-Age","3600");
		response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-with, Content-Type, Accept, Key, Authorization");
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
			response.setStatus(HttpServletResponse.SC_OK);
		}else{
			filterChain.doFilter(request,response);
		}
	}
}
