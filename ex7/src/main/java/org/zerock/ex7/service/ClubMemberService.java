package org.zerock.ex7.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.zerock.ex7.dto.ClubMemberDTO;
import org.zerock.ex7.entity.ClubMember;
import org.zerock.ex7.entity.ClubMemberRole;

import java.util.stream.Collectors;

public interface ClubMemberService {

	@PreAuthorize("hasRole('USER')")
	void updateClubMemberDTO(ClubMemberDTO clubMemberDTO);

	default ClubMember dtoToEntity(ClubMemberDTO dto) {
		ClubMember clubMember = ClubMember.builder()
				.email(dto.getEmail())
				.name(dto.getName())
				.password(dto.getPassword())
				.fromSocial(dto.isFromSocial())
				.roleSet(dto.getRoleSet().stream().map(
						str -> {
							if (str.equals("ROLE_USER")) return ClubMemberRole.USER;
							else if (str.equals("ROLE_MEMBER")) return ClubMemberRole.MANAGER;
							else if (str.equals("ROLE_ADMIN")) return ClubMemberRole.ADMIN;
							else return ClubMemberRole.USER;
						}).collect(Collectors.toSet()))
				.build();
		return clubMember;
	}

	default ClubMemberDTO entityToDTO(ClubMember clubMember) {
		ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
				.email(clubMember.getEmail())
				.name(clubMember.getName())
				.fromSocial(clubMember.isFromSocial())
				.roleSet(clubMember.getRoleSet().stream().map(
								role -> new String("ROLE_" + role.name()))
						.collect(Collectors.toSet()))
				.regDate(clubMember.getRegDate())
				.modDate(clubMember.getModDate())
				.build();
		return clubMemberDTO;
	}
}