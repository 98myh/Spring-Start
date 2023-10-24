package org.zerock.ex5.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	private String text;
	private String reply;

	//연관된 테이블 지정(1:다 관계)
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;

}
