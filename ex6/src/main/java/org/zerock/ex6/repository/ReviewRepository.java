package org.zerock.ex6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex6.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
