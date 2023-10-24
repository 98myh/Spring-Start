package org.zerock.ex5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex5.entity.Board;

public interface BordRepository extends JpaRepository<Board,Long> {
}
