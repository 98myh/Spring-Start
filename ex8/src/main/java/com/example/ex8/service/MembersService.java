package com.example.ex8.service;

import com.example.ex8.dto.MembersDTO;
import com.example.ex8.entity.Members;
import com.example.ex8.entity.MembersRole;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.stream.Collectors;

public interface MembersService {

	@PreAuthorize("hasRole('USER')")
	void updateMembersDTO(MembersDTO membersDTO);

	default Members dtoToEntity(MembersDTO dto) {
		Members members = Members.builder()
				.mno(dto.getMno())
				.id(dto.getId())
				.password(dto.getPassword())
				.name(dto.getName())
				.gender(dto.getGender())
				.email(dto.getEmail())
				.mobile(dto.getMobile())
				.myPhoto(dto.getMyPhoto())
				.birthday(dto.getBirthday())
				.fromSocial(dto.isFromSocial())
				.roleSet(dto.getRoleSet().stream().map(
						str -> {
							if (str.equals("ROLE_USER")) return MembersRole.USER;
							else if (str.equals("ROLE_MEMBER")) return MembersRole.MANAGER;
							else if (str.equals("ROLE_ADMIN")) return MembersRole.ADMIN;
							else return MembersRole.USER;
						}).collect(Collectors.toSet()))
				.build();
		return members;
	}

	default MembersDTO entityToDTO(Members members) {
		MembersDTO membersDTO = MembersDTO.builder()
				.mno(members.getMno())
				.id(members.getId())
				.name(members.getName())
				.gender(members.getGender())
				.email(members.getEmail())
				.mobile(members.getMobile())
				.myPhoto(members.getMyPhoto())
				.birthday(members.getBirthday())
				.fromSocial(members.isFromSocial())
				.regDate(members.getRegDate())
				.modDate(members.getModDate())
				.roleSet(members.getRoleSet().stream().map(
								role -> new String("ROLE_" + role.name()))
						.collect(Collectors.toSet()))
				.build();
		return membersDTO;
	}
}