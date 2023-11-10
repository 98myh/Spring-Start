package org.zerock.ex7.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.zerock.ex7.security.handler.CustomLoginSuccessHandler;
import org.zerock.ex7.security.service.ClubUserDetailsService;

import java.util.ArrayList;
import java.util.List;

// extends WebSecurityConfigurerAdapter을 상속받아서 진행되었던것이 springboot security 5.7.x 부터
// @EnableWebSecurity을 사용하고 SecurityFilterChain 관련한 @Bean으로 등록해서 사용

@Configuration
@EnableWebSecurity
@EnableMethodSecurity    // @EnableGlobalMethodSecurity는 소멸됨.
public class SecurityConfig {
	@Autowired
	private ClubUserDetailsService clubUserDetailsService;


	// 액세스를 허용하는 주소들을 등록
	private static final String[] AUTH_WHITELIST = {
			"/", "/accessDenied", "/sample/all", "/sample/login", "/sample/modify",
			"/notes/",  "/notes/all", "/auth/login", "/logout"
	};

	//비밀번호 암호화
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// security 5.7.x 부터는 @Bean으로 등록해서 사용(리턴 타입은 SecurityFilterChain)
	@Bean
	protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity
				// stateless한 rest api를 개발할 것이므로 csrf 공격에 대한 옵션은 꺼둔다.
//                .csrf(AbstractHttpConfigurer::disable)

				// HttpSecurity의 http로 url을 요구할 때 권한을 설정하는 method
				.authorizeHttpRequests((authorizeRequests) -> {
					authorizeRequests.requestMatchers(AUTH_WHITELIST).permitAll();
//                    authorizeRequests.requestMatchers("/sample/user/**").authenticated();
					authorizeRequests.requestMatchers("/sample/admin/**").hasRole("ADMIN");

					authorizeRequests.requestMatchers("/sample/member/**")
							// ROLE_은 붙이면 안 된다. hasAnyRole()을 사용할 때 자동으로 ROLE_이 붙기 때문이다.
							// hasAnyRole는 hasRole의 복수 인자 처리 형태
							.hasAnyRole("ADMIN", "MEMBER");
					authorizeRequests.requestMatchers("/auth/logout").authenticated();
					authorizeRequests.anyRequest().denyAll();
				})
				.authenticationManager(
						authenticationManager(
								httpSecurity,
								(BCryptPasswordEncoder) passwordEncoder(),
								userDetailsService()
						)
				)

				.formLogin((formLogin) -> {
					/* 권한이 필요한 요청은 해당 url로 리다이렉트 */
//                    formLogin.loginProcessingUrl("/user/login").disable();

					// 커스텀으로 할 경우 logout도 등록 해야 함
					formLogin.loginPage("/auth/login")
							.loginProcessingUrl("/user/login")
							.successHandler(customLoginSuccessHandler())
							.defaultSuccessUrl("/sample/member"); // 로그인한 후 이동할 페이지 지정
				})

				// logout 페이지 지정
				.logout(httpSecurityLogoutConfigurer -> {
					//기본 페이지 , csrf를 사용할 경우는 반드시 post방식으로만 처리됨. 따라서 별도의 페이지에서 post 방식으로 처리가 되어야함
//                    httpSecurityLogoutConfigurer.logoutUrl("/logout");

					// 커스텀
					httpSecurityLogoutConfigurer.logoutUrl("/auth/logout").logoutSuccessUrl("/");
				})

				.build();
	}


	@Bean
	public CustomLoginSuccessHandler customLoginSuccessHandler() {
		return new CustomLoginSuccessHandler(passwordEncoder());
	}


	@Bean // authenticationManager의 역할: 비밀번호 암호화, 인증과 권한을 부여하는 곳
	public AuthenticationManager authenticationManager(
			HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsService userDetailsService)
			throws Exception {
		// 객체 선언
		AuthenticationManagerBuilder authenticationManagerBuilder =
				httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

		// 겍체 빌더: 인증 권한 부여, 비밀번호 암호화
		authenticationManagerBuilder
				.userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);

		return authenticationManagerBuilder.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(clubUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
}