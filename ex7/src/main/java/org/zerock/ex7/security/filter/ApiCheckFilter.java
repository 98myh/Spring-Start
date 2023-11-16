package org.zerock.ex7.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log
public class ApiCheckFilter extends OncePerRequestFilter {

	private AntPathMatcher antPathMatcher;
	private String pattern;

	public ApiCheckFilter(String pattern) {
		antPathMatcher=new AntPathMatcher();
		this.pattern = pattern;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.info("Request URI : "+request.getRequestURI());
		if (antPathMatcher.match(request.getContextPath()+pattern,request.getRequestURI())){
			log.info("ApiCheckFilter............");
			return ;
		}
		filterChain.doFilter(request,response);
	}
}
