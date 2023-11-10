package org.zerock.ex7.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User {
//세션을 위한 DTO

	private String email;
	private String name;
	private boolean fromSocial;
	public ClubAuthMemberDTO(String username, String password,boolean fromSocial, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.email=username; //security 사용자가 생성한 계정을 데이터베이스의 계정과 연결하는 매우 중요한 부분
		this.fromSocial=fromSocial;
	}
}
