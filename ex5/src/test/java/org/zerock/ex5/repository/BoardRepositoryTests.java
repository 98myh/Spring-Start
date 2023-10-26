package org.zerock.ex5.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.ex5.dto.PageRequestDTO;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
	@Autowired
	private BoardRepository boardRepository;

	@Test
	public void insertBoard(){
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder()
					.email("user"+i+"@aaa.com")
					.build();

			Board board = Board.builder()
					.title("Title...."+i)
					.content("Content...."+i)
					.writer(member)
					.build();

			boardRepository.save(board);
		});
	}


	@Test
	public void testSearch1(){
		boardRepository.search1();

		Pageable pageable= PageRequest.of(0,10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
		Page<Object[]> result=boardRepository.searchPage("t","1",pageable);
	}
}