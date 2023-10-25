package org.zerock.ex5.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex5.dto.BoardDTO;
import org.zerock.ex5.dto.PageRequestDTO;
import org.zerock.ex5.dto.PageResultDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTests {
	@Autowired
	BoardService boardService;

	@Test
	public void register() {
		BoardDTO dto = BoardDTO.builder()
				.title("Test.")
				.content("Contet.")
				.writerEmail("user55@aaa.com")
				.build();
		Long bno= boardService.register(dto);
		System.out.println(bno);
	}

	@Test
	public void getList(){
		PageRequestDTO pageRequestDTO=new PageRequestDTO();
		PageResultDTO<BoardDTO,Object[]> result=boardService.getList(pageRequestDTO);
		for (BoardDTO dto:result.getDtoList()){
			System.out.println(dto);
		}
	}

	@Test public void get(){
		BoardDTO dto=boardService.get(100L);
		System.out.println(dto);
	}

	@Test
	public void remove(){
		boardService.removeWithReplies(100L);
	}

	@Test
	public void modify(){
		BoardDTO boardDTO=BoardDTO.builder()
				.bno(2L)
				.title("changed title")
				.content("changed content")
				.build();
		boardService.modify(boardDTO);
	}

}