package org.zerock.ex7.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.ex7.entity.ClubMember;
import org.zerock.ex7.repository.ClubMemberRepository;
import org.zerock.ex7.security.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
	private final ClubMemberRepository clubMemberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Service Test : " + username);
		Optional<ClubMember> result=clubMemberRepository.findByEmail(username,false);

		//해당 사용자가 db에 없으면 예외처리
		if (!result.isPresent()){
			throw new UsernameNotFoundException("Check Email or Social");
		}

		//해당 사용자가 있을 경우
		ClubMember clubMember=result.get();
		ClubAuthMemberDTO clubAuthMemberDTO=new ClubAuthMemberDTO(
				clubMember.getEmail(),
				clubMember.getPassword(),
				clubMember.isFromSocial(),
				clubMember.getRoleSet().stream().map(role->
						new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList())
		);
		clubAuthMemberDTO.setName(clubMember.getName());
		clubAuthMemberDTO.setFromSocial(clubMember.isFromSocial());

		return clubAuthMemberDTO;//정보를 세션으로 넘김
	}
}
