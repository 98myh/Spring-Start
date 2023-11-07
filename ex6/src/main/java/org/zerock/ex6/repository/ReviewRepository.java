package org.zerock.ex6.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ex6.entity.Member;
import org.zerock.ex6.entity.Movie;
import org.zerock.ex6.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

	//특정 영화의 모든 리뷰와 회원의 닉네임
	@EntityGraph(attributePaths = {"member"},type = EntityGraph.EntityGraphType.FETCH) //LAZY방식이어서 한번에 review와 member를 조회 할 수 없기때문에 추가해주어야함
	List<Review> findByMovie(Movie movie);

	//특정 멤버의 리뷰 전체 삭제(쿼리 메서드는 갯수만큼 반복 수행하지만, 쿼리(@)는 한번만 수행 )
	@Modifying
	@Query("delete from Review mr where mr.member=:member")
	void deleteByMember(Member member);

	@Modifying //update, deltet할 때 항상 표기
	@Query("delete from Review r where r.movie.mno=:mno")
	void deleteByMno(@Param("mno") Long mno);

}
