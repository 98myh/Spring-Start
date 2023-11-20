package org.zerock.ex7.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.ex7.security.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;

@Log
public class ApiCheckFilter extends OncePerRequestFilter {

	private AntPathMatcher antPathMatcher;
	private String pattern;
	private JWTUtil jwtUtil;

	public ApiCheckFilter(String pattern,JWTUtil jwtUtil) {
		antPathMatcher=new AntPathMatcher();
		this.pattern = pattern;
		this.jwtUtil=jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.info("Request URI : "+request.getRequestURI());
		if (antPathMatcher.match(request.getContextPath()+pattern,request.getRequestURI())){
			log.info("ApiCheckFilter............");
			boolean checkHeader=checkAuthHeader(request);
			if(checkHeader){
				filterChain.doFilter(request,response);
				return;
			}else{
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("application/json;charset=utf-8");
				JSONObject jsonObject=new JSONObject();

				String message="FAIL CHECK API TOKEN";
				jsonObject.put("code","403");
				jsonObject.put("message",message);
				PrintWriter printWriter=response.getWriter();
				printWriter.print(jsonObject);
				return;
			}
		}
		filterChain.doFilter(request,response);
	}

	private boolean checkAuthHeader(HttpServletRequest request){
		boolean checkResult=false;
		String authHeader=request.getHeader("Authorization");
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
			log.info("Authorization exist : "+authHeader);
			try{
				String email=jwtUtil.validateAndExtract(authHeader.substring(7)); //Bearer 가 있는 위치때문 7번째부터 때어옴
				log.info("validate result :"+email);
				checkResult=email.length()>0;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return checkResult;
	}
}
