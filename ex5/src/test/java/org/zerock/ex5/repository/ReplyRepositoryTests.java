package org.zerock.ex5.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.entity.Member;
import org.zerock.ex5.entity.Reply;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
	@Autowired
	private ReplyRepository replyRepository;

	@Test
	public void insertReply() {
		IntStream.rangeClosed(1, 300).forEach(i -> {

			long bno = (long) (Math.random() * 100) + 1;

			Board board = Board.builder()
					.bno(bno)
					.build();

			Reply reply = Reply.builder()
					.text("Reply...." + i)
					.board(board)
					.replyer("guest")
					.build();

			replyRepository.save(reply);
		});
	}

	@Test
	public void testListByBoard(){
		List<Reply> result=replyRepository.getRepliesByBoardOrderByRno(
				Board.builder()
						.bno(97L)
						.build()
		);
		result.forEach(r-> System.out.println(r));
	}
}