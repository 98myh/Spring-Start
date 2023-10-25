package org.zerock.ex5.service;

import org.zerock.ex5.dto.BoardDTO;
import org.zerock.ex5.dto.PageRequestDTO;
import org.zerock.ex5.dto.PageResultDTO;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.entity.Member;

public interface BoardService {
	Long register(BoardDTO dto);

	//목록 보기
	//entity를 받기때문에 objext[]
	PageResultDTO<BoardDTO,Object[]>getList(PageRequestDTO pageRequestDTO);

	//상세보기
	BoardDTO get(Long bno);

	//삭제하기
	void removeWithReplies(Long bno);

	//수정하기
	void modify(BoardDTO dto);

	default Board dtoToEntity(BoardDTO dto){
		Member member=Member.builder()
				.email(dto.getWriterEmail())
				.build();
		Board board=Board.builder()
				.bno(dto.getBno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(member)
				.build();
		return board;
	}
	default BoardDTO entityToDTO(Board board,Member member,Long replyCount){
		BoardDTO dto=BoardDTO.builder()
				.bno(board.getBno())
				.title(board.getTitle())
				.content(board.getContent())
				.regDate(board.getRegDate())
				.modDate(board.getModDate())
				.writerEmail(member.getEmail())
				.writerName(member.getName())
				.replyCount(replyCount.intValue())
				.build();
		return dto;
	}
}
