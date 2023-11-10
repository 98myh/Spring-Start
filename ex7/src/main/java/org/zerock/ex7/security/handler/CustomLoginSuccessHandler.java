package org.zerock.ex7.security.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private PasswordEncoder passwordEncoder;

	public CustomLoginSuccessHandler(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		List<String> roleNames = new ArrayList<>();

		auth.getAuthorities().forEach(grantedAuthority -> {
			roleNames.add(grantedAuthority.getAuthority());
		});
		log.info(">>"+roleNames);

		if (roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect(request.getContextPath()+"/sample/admin");
			return;
		}
		if (roleNames.contains("ROLE_MANAGER")) {
			response.sendRedirect(request.getContextPath()+"/sample/member");
			return;
		}
		if (roleNames.contains("ROLE_USER")) {
			response.sendRedirect(request.getContextPath()+"/sample/all");
			return;
		}
		response.sendRedirect("/");
	}

}