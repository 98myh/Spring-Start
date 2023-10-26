package org.zerock.ex5.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.ex5.dto.BoardDTO;
import org.zerock.ex5.dto.PageRequestDTO;
import org.zerock.ex5.dto.PageResultDTO;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.entity.Member;
import org.zerock.ex5.repository.BoardRepository;
import org.zerock.ex5.repository.MemberRepository;
import org.zerock.ex5.repository.ReplyRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
	private final BoardRepository boardRepository; //순환참조 막기위해 final
	private final ReplyRepository replyRepository;
	private final MemberRepository memberRepository;
	@Override
	@Transactional
	public Long register(BoardDTO dto) {
		Board board=dtoToEntity(dto);

		if(memberRepository.findById(dto.getWriterEmail()).isEmpty()){
			return -1L;
		}else {
			boardRepository.save(board);
		}
		//몇번 글이 등록됬는지 리턴
		return board.getBno();
	}

	//목록보기
	@Override
	public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		Function<Object[],BoardDTO> fn=(en->entityToDTO((Board)en[0],(Member) en[1],(Long) en[2]));
//		Page<Object[]> result=boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
//		return new PageResultDTO<>(result,fn);
		Page<Object[]> result=boardRepository.searchPage(
				pageRequestDTO.getType(),
				pageRequestDTO.getKeyword(),
				pageRequestDTO.getPageable(Sort.by("bno").descending()));
		return new PageResultDTO<>(result,fn);
	}

	//상세보기
	@Override
	public BoardDTO get(Long bno) {
		Object result=boardRepository.getBoardByBno(bno);
		Object[] arr=(Object[]) result;
		return entityToDTO((Board) arr[0],(Member) arr[1],(Long) arr[2]);
	}

	//삭제하기
	@Transactional
	@Override
	public void removeWithReplies(Long bno) {
		replyRepository.deleteByBno(bno);
		boardRepository.deleteById(bno);
	}

	//수정하기
	@Override
	public void modify(BoardDTO dto) {
		Optional<Board> result=boardRepository.findById(dto.getBno());
		if(result.isPresent()){
			Board board=result.get();
			board.changeTitle(dto.getTitle());
			board.changeContent(dto.getContent());
			log.info("board"+board);
			boardRepository.save(board);

		}
	}
}
