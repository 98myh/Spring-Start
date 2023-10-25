package org.zerock.ex5.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")//엔터티가 연관되는 속성은 반드시 제외
public class Board extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)//EAGER(default값) - 무조건 조인을 함 느림, LAZY - 필요할때만 조인 귀찮아지는경우가 있음
	private Member writer;

	public void changeTitle(String title){
		this.title=title;
	}

	public void changeContent(String content){
		this.content=content;
	}
}
