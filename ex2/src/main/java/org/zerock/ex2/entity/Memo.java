package org.zerock.ex2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // JPA로 관리되는 엔티티 객체 의미
@Table(name = "tbl_memo") // 테이블 생성을 의미
@ToString //값을 String으로 바꿔줌
@Getter
@AllArgsConstructor //생성할때 매개변수가 있는경우
@NoArgsConstructor //생성할때 매개변수가 없는경우
@Builder // 빌더 사용하기 위해서는 @AllArgsConstructor , @NoArgsConstructor 둘 다 사용해야함 , 생성할때 .속성으로 접근해서 값을 넣어준다
public class Memo {
	@Id //pk지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가 , GenerationType.IDENTITY=구분되어지는 값
	private Long mno;

	@Column(length = 200,nullable = false)//추가적인 필드 생성
	private String memoText;

	//@Setter 대신 사용하기 위함
	public void changeMemoTest(String str){
		memoText=str;
	}
}
