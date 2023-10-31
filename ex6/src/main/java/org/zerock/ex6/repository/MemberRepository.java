package org.zerock.ex6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex6.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
