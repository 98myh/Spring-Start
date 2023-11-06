package org.zerock.ex6.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.ex6.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
	//coalesce == 오라클에서 NVL과 같은 첫 인자가 NULL이면 두번째 인자 출력
//	@Query("select m,avg(coalesce(r.grade,0)),count(distinct r) from Movie m left outer join Review r on r.movie=m group by m")

	//mi로 인해서 가장 먼저 입력된 MovieImage가 출력됨
//	@Query("select m,mi,avg(coalesce(r.grade,0)),count(distinct r) "+
//			" from Movie m "+
//			" left outer join MovieImage mi on mi.movie=m "+
//			" left outer join Review r on r.movie=m group by m")

	//MovieImage가 가장 최근에 등록된 MovieImage를 들고 오는 쿼리로 변경
//	@Query("select m,i,count(r) "+
//			"from Movie m "+
//			"left join MovieImage i on i.movie=m "+
//			"and i.inum=(select max(i2.inum) from MovieImage i2 where i2.movie=m) "+
//			"left outer join Review r on r.movie=m group by m")
//	Page<Object[]> getListPage(Pageable pageable);


	//평균과 평점을 추가한 getListPage
	@Query("select m,mi,avg(coalesce(r.grade,0)),count(distinct r) "+
			"from Movie m "+
			"left outer join MovieImage mi on mi.movie=m "+
			"left outer join Review r on r.movie=m "+
			"group by m"
	)
	Page<Object[]> getListPage(Pageable pageable);

	//특정 영화의 모든 이미지와 평균 평점/리뷰 개수
	@Query("select m,mi,avg(coalesce(r.grade,0)),count(r) "+
			"from Movie m "+
			"left outer join MovieImage mi on mi.movie=m "+
			"left outer join Review r on r.movie=m "+
			"where m.mno=:mno "+
			"group by mi"
	)
	List<Object[]> getMovieWithAll(Long mno);




}
