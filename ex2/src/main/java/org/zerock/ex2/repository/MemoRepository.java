package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;

//<클래스,pk>
public interface MemoRepository extends JpaRepository<Memo,Long> {
	List<Memo> findByMnoBetweenOrderByMnoDesc(Long from,Long to); //메서드명 자체가 쿼리(쿼리 메서드)
	Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable); //메서드명 자체가 쿼리(쿼리 메서드)
	void deleteMemoByMnoLessThan(Long mno);

	//쿼리문의 테이블명은 객체명을 사용해줘야함
	@Query("select m from Memo m order by m.mno desc")
	List<Memo> getListDesc();

	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText= :memoText where m.mno= :mno")
	int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

	@Query(value="select m from Memo m where m.mno>:mno",countQuery ="select count(m) from Memo m where m.mno>:mno")
	Page<Memo> getListWithQueryPageable(Long mno,Pageable pageable);

	@Query(value="select m.mno,m.memoText,CURRENT_DATE from Memo m where m.mno> :mno",countQuery = "select count(m) from Memo m where m.mno> :mno")
	Page<Object> getListWithQueryObject(Long mno,Pageable pageable); //CURRENT_DATE의 타입을 지정할 수 없기때문에 <Object>로 한다 => 객체에 담을수 없는 경우에 <Object> 사용

	//일반 sql쿼리문 처럼 사용할 경우
	@Query(value="select * from tbl_memo where mno>0",nativeQuery = true)
	List<Memo> getNativeQueryResult();
}
