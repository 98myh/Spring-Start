package com.example.ex8.security.service;

import com.example.ex8.entity.Members;
import com.example.ex8.entity.MembersRole;
import com.example.ex8.repository.MembersRepository;
import com.example.ex8.security.dto.AuthMembersDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
@RequiredArgsConstructor
public class OAuth2MembersDetailsService extends DefaultOAuth2UserService {
	private final MembersRepository membersRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("OAuth2MembersDetailsService Test load User : "+userRequest);
		String clientName=userRequest.getClientRegistration().getClientName();
		log.info(userRequest.getAdditionalParameters()+"");//구글로 부터 오는 정보를 확인하기 위해 -> sub,picture,email,email_verified
		OAuth2User oAuth2User=super.loadUser(userRequest);
		log.info("======================");
		oAuth2User.getAttributes().forEach((k,v)->{
			log.info(k+" : "+v);
		});

		String email=null;

		if (clientName.equals("Google")){
			email=oAuth2User.getAttribute("email");
		}
		log.info("email ==> "+email);
		Members members=saveSocialMember(email);
		AuthMembersDTO authMembersDTO=new AuthMembersDTO(
				members.getEmail(),
				members.getPassword(),
				true,
				members.getRoleSet().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList())
		);
		authMembersDTO.setName(members.getName());

		return authMembersDTO;
	}

	private Members saveSocialMember(String email) {
		Optional<Members> result=membersRepository.findByEmail(email,true);
		if (result.isPresent()){
			return result.get();
		}
		Members members=Members.builder()
				.email(email)
				.password(passwordEncoder.encode("1"))
				.fromSocial(true)
				.build();
		members.addMembersRole(MembersRole.USER);
		membersRepository.save(members);

		return members;
	}

}
