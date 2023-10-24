package org.zerock.ex5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex5.entity.Member;

public interface MemberRepository extends JpaRepository<Member,String> {

}
