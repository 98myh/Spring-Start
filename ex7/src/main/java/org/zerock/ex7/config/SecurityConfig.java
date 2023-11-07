package org.zerock.ex7.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity // springboot security 5.7.x 부터는 @Bean으로 등록해서 사용
@EnableMethodSecurity
public class SecurityConfig {

}