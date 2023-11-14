package org.zerock.ex7.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.ex7.entity.ClubMember;
import org.zerock.ex7.entity.ClubMemberRole;
import org.zerock.ex7.repository.ClubMemberRepository;
import org.zerock.ex7.security.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {
	private final ClubMemberRepository clubMemberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("Test load User : "+userRequest);
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
		ClubMember clubMember=saveSocialMember(email);
		ClubAuthMemberDTO clubAuthMemberDTO=new ClubAuthMemberDTO(
				clubMember.getEmail(),
				clubMember.getPassword(),
				true,
				clubMember.getRoleSet().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList())
		);
		clubAuthMemberDTO.setName(clubMember.getName());

		return clubAuthMemberDTO;
	}

	private ClubMember saveSocialMember(String email) {
		Optional<ClubMember> result=clubMemberRepository.findByEmail(email,true);
		if (result.isPresent()){
			return result.get();
		}
		ClubMember clubMember=ClubMember.builder()
				.email(email)
				.password(passwordEncoder.encode("1"))
				.fromSocial(true)
				.build();
		clubMember.addMemberRole(ClubMemberRole.USER);
		clubMemberRepository.save(clubMember);

		return clubMember;
	}


}
