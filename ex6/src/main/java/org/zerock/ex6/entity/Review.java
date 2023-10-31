package org.zerock.ex6.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@ToString(exclude = {"member","movie"})
public class Review extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewnum;

	@ManyToOne(fetch=FetchType.LAZY)
	private Movie movie;

	@ManyToOne(fetch=FetchType.LAZY)
	private Member member;

	private int grade;

	private String text;

}
