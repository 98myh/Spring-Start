package com.example.ex8.service;

import com.example.ex8.dto.MembersDTO;
import com.example.ex8.entity.Members;
import com.example.ex8.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MembersServiceImpl implements MembersService {
	private final PasswordEncoder passwordEncoder;
	private final MembersRepository membersRepository;

	@Override
	public void updateMembersDTO(MembersDTO membersDTO) {
		log.info("Update clubMemberDTO: " + membersDTO);
		membersDTO.setPassword(passwordEncoder.encode(membersDTO.getPassword()));
		Members members = membersRepository.save(dtoToEntity(membersDTO));
		log.info("Update Members: " + members);
	}
}