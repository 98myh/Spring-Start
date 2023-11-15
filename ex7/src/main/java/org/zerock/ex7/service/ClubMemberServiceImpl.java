package org.zerock.ex7.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.ex7.dto.ClubMemberDTO;
import org.zerock.ex7.entity.ClubMember;
import org.zerock.ex7.repository.ClubMemberRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService{
	private final PasswordEncoder passwordEncoder;
	private final ClubMemberRepository repository;

	@Override
	public void updateClubMemberDTO(ClubMemberDTO dto) {
		log.info("Update clubMemberDTO: " + dto);
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		ClubMember cm = repository.save(dtoToEntity(dto));
		log.info("Update clubMember: " + cm);
	}
}