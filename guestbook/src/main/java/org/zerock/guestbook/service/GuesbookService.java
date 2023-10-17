package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuesbookService {
	Long register(GuestbookDTO dto);

	default Guestbook dtoToEntity(GuestbookDTO dto){
//		Guestbook entity=Guestbook.builder()
//				.gno(dto.getGno())
//				.title(dto.getTitle())
//				.content(dto.getContent())
//				.writer(dto.getWriter())
//				.build();
//		return entity;
		return Guestbook.builder()
				.gno(dto.getGno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(dto.getWriter())
				.build();
	};
}
