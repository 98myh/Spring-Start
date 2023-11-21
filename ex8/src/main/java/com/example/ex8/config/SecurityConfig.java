package com.example.ex8.config;

import com.example.ex8.security.filter.ApiCheckFilter;
import com.example.ex8.security.filter.ApiLoginFilter;
import com.example.ex8.security.handler.ApiLoginFailHandler;
import com.example.ex8.security.handler.CustomAccessDeniedHandler;
import com.example.ex8.security.handler.CustomLoginSuccessHandler;
import com.example.ex8.security.handler.CustomLogoutSuccessHandler;
import com.example.ex8.security.service.MembersDetailsService;
import com.example.ex8.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


// Spring Security를 Database을 이용하여 처리한 코드
// extends WebSecurityConfigurerAdapter을 상속받아서 진행되었던것이 springboot security 5.7.x 부터
// @EnableWebSecurity을 사용하고 SecurityFilterChain 관련한 @Bean으로 등록해서 사용

@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity    // @EnableGlobalMethodSecurity는 소멸됨.
public class SecurityConfig {

	@Autowired
	private MembersDetailsService membersDetailsService;

	// 액세스를 허용하는 주소들을 등록
	private static final String[] AUTH_WHITELIST = {"/", "/sample/all", "/auth/login", "/auth/logout",
			"/auth/accessDenied", "/notes/**", "/notes/**/*", "/notes/all",};


	// 암호화시키기 위한 빈 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//시큐리티 설정
	@Bean // security 설정, 5.7.x부터 @Bean으로 등록해서 사용(리턴 타입 SecurityFilterChain)
	protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
		// httpSecurity의 http로 url을 요구할 때 권한을 매치하는 곳
		httpSecurity.authorizeHttpRequests(auth -> {
			log.info("auth>>" + auth);
			auth.requestMatchers(AUTH_WHITELIST).permitAll() // 시큐리티 없이 접근 가능하도록 등록
					.requestMatchers("/sample/admin").hasRole("ADMIN").requestMatchers("/sample/member").access(
							// 복수개의 권한을 등록할 때
							new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('MANAGER')"))
					.requestMatchers("/sample/modify").access(
							// 복수개의 권한을 등록할 때
							new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
					)
					//new WebExpressionAuthorizationManager("hasAnyRole('ADMIN','MEMBER')")
					.anyRequest().denyAll(); // 그외는 모두 접근 금지
		});

		// social 로그인 할 경우
		httpSecurity.oauth2Login(new Customizer<OAuth2LoginConfigurer<HttpSecurity>>() {
			@Override
			public void customize(OAuth2LoginConfigurer<HttpSecurity> httpSecurityOAuth2LoginConfigurer) {
				httpSecurityOAuth2LoginConfigurer.loginPage("/auth/login");
//       httpSecurityOAuth2LoginConfigurer.loginProcessingUrl("");
				httpSecurityOAuth2LoginConfigurer.successHandler(customLoginSuccessHandler());
			}
		});

		// database 로그인 할 경우
		httpSecurity.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
			@Override
			public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
				httpSecurityFormLoginConfigurer.loginPage("/auth/login");
				httpSecurityFormLoginConfigurer.loginProcessingUrl("/login");
				httpSecurityFormLoginConfigurer.successHandler(customLoginSuccessHandler());
			}
		});
		httpSecurity.logout(new Customizer<LogoutConfigurer<HttpSecurity>>() {
			@Override
			public void customize(LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer) {
				httpSecurityLogoutConfigurer.logoutUrl("/auth/logout") // csrf사용시 form의 post와 action주소와 "/auth/logout" 일치!
						.logoutSuccessUrl("/").logoutSuccessHandler(customLogoutSuccessHandler()).invalidateHttpSession(true);
			}
		});
		httpSecurity.csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
			@Override  // 서버에 인증정보를 저장하지 않기 때문에 csrf를 사용하지 않는다.
			public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
				// disable이 안된 상태에서는 logout도 반드시 post방식으로 처리가 되어야 함.
				httpSecurityCsrfConfigurer.disable();  //disable하면 logout을 get으로 접근해도 처리가 됨.
			}
		});
		httpSecurity.exceptionHandling(new Customizer<ExceptionHandlingConfigurer<HttpSecurity>>() {
			@Override
			public void customize(ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer) {
				httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(customAccessDeniedHandler());
			}
		});
		httpSecurity.rememberMe(new Customizer<RememberMeConfigurer<HttpSecurity>>() {
			@Override
			public void customize(RememberMeConfigurer<HttpSecurity> httpSecurityRememberMeConfigurer) {
				httpSecurityRememberMeConfigurer.tokenValiditySeconds(60 * 60 * 24 * 7);
				// @Bean을 이용할 경우
				// httpSecurityRememberMeConfigurer.rememberMeServices(rememberMeServices(clubUserDetailsService));
			}
		});
		//httpSecurity가 자신이 가지고 있는 AuthenticationConfiguration를 가져온것 , apiLoginFilter는 apiCheckFilter로 갈때 토큰을 주기위한 필터 ,apiCheckFilter는 요청한 데이터를 주기 위한 필터
		httpSecurity.addFilterBefore(apiLoginFilter(httpSecurity.getSharedObject(AuthenticationConfiguration.class)), UsernamePasswordAuthenticationFilter.class);
		httpSecurity.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
		//httpSecurity.addFilterBefore(new ExceptionHandlerFilter(), BasicAuthenticationFilter.class);
		return httpSecurity.build();
	}

	@Bean
	public ApiCheckFilter apiCheckFilter(){
		return new ApiCheckFilter("/notes/**/*",jwtUtil());
	}

	@Bean
	public ApiLoginFilter apiLoginFilter(AuthenticationConfiguration conf) throws Exception{
		ApiLoginFilter apiLoginFilter=new ApiLoginFilter("/api/login",jwtUtil());
		apiLoginFilter.setAuthenticationManager(conf.getAuthenticationManager());
		apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
		return apiLoginFilter;
	}

	@Bean
	public JWTUtil jwtUtil(){
		return new JWTUtil();
	}


	@Bean
	public CustomLoginSuccessHandler customLoginSuccessHandler() {
		return new CustomLoginSuccessHandler(passwordEncoder());
	}

	@Bean
	public LogoutSuccessHandler customLogoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Bean
	public AccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}


}
