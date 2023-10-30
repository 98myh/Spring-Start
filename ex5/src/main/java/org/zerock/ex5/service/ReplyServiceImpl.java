package org.zerock.ex5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.ex5.dto.ReplyDTO;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.entity.Reply;
import org.zerock.ex5.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	private final ReplyRepository replyRepository;

	@Override
	public Long register(ReplyDTO replyDTO) {
		Reply reply = dtoToEntity(replyDTO);
		replyRepository.save(reply);
		//몇번이 등록됬는지 반환
		return reply.getRno();
	}

	@Override
	public List<ReplyDTO> getList(Long bno) {
		List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(
				Board.builder().bno(bno).build()
		);
		return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
	}

	@Override
	public void modify(ReplyDTO replyDTO) {
		Reply reply = dtoToEntity(replyDTO);
		replyRepository.save(reply);
	}

	@Override
	public void remove(Long rno) {
		replyRepository.deleteById(rno);
	}
}