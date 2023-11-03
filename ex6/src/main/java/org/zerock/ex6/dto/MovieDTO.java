package org.zerock.ex6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
	private long mno;
	private String title;

	//데이터가 없을때 이렇게(ArrayList<>()) 초기값을 주겠다
	@Builder.Default //빌더 패턴을 통해 인스턴스를 만들 때 특정 필드를 특정 값으로 초기화 - 아래는 ArrayList<>()를 초기값으로 하겠다.
	private List<MovieImageDTO> imageDTOList=new ArrayList<>();

	//영화의 평균 평점
	private double avg;
	//리뷰 수
	private int reviewCnt;

	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
