package org.zerock.ex6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex6.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {
}
