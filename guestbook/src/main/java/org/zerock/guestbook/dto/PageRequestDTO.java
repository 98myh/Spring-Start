package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
	private int page;
	private int size;

	//페이지 요청을 위한 객체
	public PageRequestDTO() {
		this.page=1;
		this.size=10;
	}

	//jpa는 페이지 시작시 0부터 시작
	public Pageable getPageable(Sort sort){
		return PageRequest.of(page -1,size,sort);
	}
}
