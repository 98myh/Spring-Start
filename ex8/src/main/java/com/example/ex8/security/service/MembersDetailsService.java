package com.example.ex8.security.service;

import com.example.ex8.entity.Members;
import com.example.ex8.repository.MembersRepository;
import com.example.ex8.security.dto.AuthMembersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

// UserDetailsService는 데이터베이스로 부터 인증한 뒤에 세션을 획득하기위한 클래스
@Service
@RequiredArgsConstructor
public class MembersDetailsService implements UserDetailsService {
	private final MembersRepository membersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("MembersDetailsService Test : " + username);
		Optional<Members> result=membersRepository.findByEmail(username,false);

		//해당 사용자가 db에 없으면 예외처리
		if (!result.isPresent()){
			throw new UsernameNotFoundException("Check Email or Social");
		}

		//해당 사용자가 있을 경우
		Members members=result.get();
		AuthMembersDTO authMembersDTO=new AuthMembersDTO(
				members.getEmail(),
				members.getPassword(),
				members.isFromSocial(),
				members.getRoleSet().stream().map(role->
						new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList())
		);
		authMembersDTO.setName(members.getName());
		authMembersDTO.setFromSocial(members.isFromSocial());

		return authMembersDTO;//정보를 세션으로 넘김
	}
}
