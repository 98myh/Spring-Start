package org.zerock.ex5.dto;


import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//페이지의 결과를 담기 위한 객체
@Data
public class PageResultDTO<DTO,EN> {
	private List<DTO> dtoList; //페이지 내용
	private int page;//현재 페이지
	private int size;//페이지별 사이즈
	private int totalPage;//총 페이지
	private int start,end; //시작페이지 번호, 끝 페이지 번호
	private boolean prev,next;//이전, 다음페이지 가능한지
	private List<Integer> pageList; //Pagenation 페이지 번호 목록

	//다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용해서 지정
	//생성자를 통해 dtoList를 초기화
	//Page<EN>은 하나의 항목(하나의 행) EN은 엔터티라는 뜻이지만 Object -> 어떤 값이 들어올지 모르기때문
	public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){ // Function<EN,DTO>는 엔티티를 DTO로 변환해 주는 기능
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		totalPage = result.getTotalPages(); // getTotalPages는 Page안에 있는 메서드
		makePageList(result.getPageable());
	}

	private void makePageList(Pageable pageable){
		page=pageable.getPageNumber()+1; //page는 0부터 시작하기 때문
		size=pageable.getPageSize();
		int tempEnd=(int)(Math.ceil(page/10.0))*10; //계산에의한 형식적인 끝 페이지를 지정
		start=tempEnd-9; //Pagenation의 시작
		prev=start>1;
		end=totalPage>tempEnd?tempEnd:totalPage; // Pagenation의 끝 번호
		next=totalPage>tempEnd;
		pageList= IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList()); //Pagenation , boxed는 int[] -> Integer[]로 변환
	}

}
