package org.zerock.ex5.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex5.entity.Member;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberRepositoryTests {
	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void insertMany(){
		IntStream.rangeClosed(1,100).forEach(i->{
			Member member=Member.builder()
					.email("user"+i+"@aaa.com")
					.password("1")
					.name("USER"+i)
					.build();
			memberRepository.save((member));
		});
	}
}