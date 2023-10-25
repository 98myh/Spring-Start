package org.zerock.ex5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.ex5.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
	@Modifying //update,delete를 할 때 붙인다.
	@Query("delete from Reply r where r.board.bno=:bno ")
	void deleteByBno(Long bno);
}
