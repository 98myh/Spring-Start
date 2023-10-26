package org.zerock.ex5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.repository.search.SearchBoardRepository;

public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository {
	@Query(value="select b,w,count(r) from Board b left join b.writer w left join Reply r "+
			"on r.board=b group by b ",countQuery="select count(b) from Board b ")
	Page<Object[]> getBoardWithReplyCount(Pageable pageable);

	//상세 페이지
	@Query("select b,w,count(r) from Board b left join b.writer w left outer join Reply r "+
	"on r.board=b where b.bno=:bno ")
	Object getBoardByBno(@Param("bno") Long bno);
}
