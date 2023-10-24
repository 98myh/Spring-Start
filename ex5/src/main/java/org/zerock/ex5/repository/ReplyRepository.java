package org.zerock.ex5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex5.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

}
