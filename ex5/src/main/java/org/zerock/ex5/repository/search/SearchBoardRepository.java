package org.zerock.ex5.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex5.entity.Board;

public interface SearchBoardRepository {
	Board search1();

	Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
