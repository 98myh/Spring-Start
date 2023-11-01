package org.zerock.ex6.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex6.entity.Member;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTests {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ReviewRepository reviewRepository;

	@Test
	public void insertMembers(){
		IntStream.rangeClosed(1,100).forEach(i->{
			Member member=Member.builder()
					.email("r"+i+"@a.com")
					.pw("1")
					.nickname("reviewer"+i)
					.build();
			memberRepository.save(member);
		});
	}

	@Test
	@Transactional
	public void testDeleteMember(){
		Long mid=1L;
		Member member=Member.builder()
				.mid(mid)
				.build();
		reviewRepository.deleteByMember(member);
		memberRepository.deleteById(member.getMid());
	}
}