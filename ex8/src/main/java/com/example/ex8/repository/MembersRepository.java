package com.example.ex8.repository;

import com.example.ex8.entity.Members;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members,String> {
	@EntityGraph(attributePaths = {"roleSet"},type = EntityGraph.EntityGraphType.LOAD)
	@Query("select m from Members m where m.fromSocial=:social and m.email=:email")
	Optional<Members> findByEmail(String email,boolean social);

	@EntityGraph(attributePaths = {"roleSet"},type = EntityGraph.EntityGraphType.LOAD)
	@Query("select m from Members m where m.fromSocial=:social and m.id=:id")
	Optional<Members> findById(String id,boolean social);
}
