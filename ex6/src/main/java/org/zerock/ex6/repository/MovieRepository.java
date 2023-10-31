package org.zerock.ex6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex6.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
