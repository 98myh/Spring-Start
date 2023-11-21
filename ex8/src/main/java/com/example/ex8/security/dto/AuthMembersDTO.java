package com.example.ex8.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class AuthMembersDTO extends User implements OAuth2User {
	//세션을 위한 DTO
	private String id;
	private String email;
	private String password;
	private String name;
	private boolean fromSocial;
	private Map<String,Object> attr;

	public AuthMembersDTO(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
		this(username,password,fromSocial,authorities);
		this.attr=attr;
	}

	public AuthMembersDTO(String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.email=username; //security 사용자가 생성한 계정을 데이터베이스의 계정과 연결하는 매우 중요한 부분
		id=username;
		this.password=password;
		this.fromSocial=fromSocial;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attr;
	}
}
