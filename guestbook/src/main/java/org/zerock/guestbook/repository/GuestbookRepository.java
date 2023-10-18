package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.guestbook.entity.Guestbook;

//JpaRepository<데이터,키값>
public interface GuestbookRepository extends JpaRepository<Guestbook,Long>, QuerydslPredicateExecutor<Guestbook> {

}
