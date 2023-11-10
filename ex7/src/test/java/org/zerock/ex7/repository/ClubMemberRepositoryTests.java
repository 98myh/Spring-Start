package org.zerock.ex7.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.ex7.entity.ClubMember;
import org.zerock.ex7.entity.ClubMemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClubMemberRepositoryTests {

	@Autowired
	private ClubMemberRepository clubMemberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void insertDummies(){
		IntStream.rangeClosed(1,100).forEach(i->{
			ClubMember clubMember=ClubMember.builder()
					.email("user"+i+"@zerock.org")
					.name("사용자"+i)
					.fromSocial(false)
					.password(passwordEncoder.encode("1"))
					.build();
			clubMember.addMemberRole(ClubMemberRole.USER);
			if (i>80){
				clubMember.addMemberRole(ClubMemberRole.MANAGER);
			}
			if (i>90){
				clubMember.addMemberRole(ClubMemberRole.ADMIN);
			}
			clubMemberRepository.save(clubMember);
		});

	}

	@Test
	public void testRead(){
		Optional<ClubMember> result=clubMemberRepository.findByEmail("user95@zerock.org",false);
		ClubMember clubMember=result.get();
		System.out.println(clubMember);
	}
}