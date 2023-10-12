package org.zerock.ex2.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@Log4j2
@SpringBootTest
class MemoRepositoryTests {
	@Autowired //인터페이스 자동 초기화 시켜줌 -> Proxy객체
	MemoRepository memoRepository;

	@Test
	public void testClass(){
		System.out.println(memoRepository.getClass().getName());
	}

	@Test
	public void testInsertDummies(){
		IntStream.rangeClosed(1,100).forEach(i -> {
			Memo memo=Memo.builder().memoText("Sample..."+i).build();
			memoRepository.save(memo);
		});
	}


	@Test
	public void testSelect(){
		Long mno=100L;
		Optional<Memo> result=memoRepository.findById(mno); //Optional<Memo>사용하여 값이 없으면 null 출력,에러 출력x
		if (!result.isEmpty()){
			Memo memo=result.get();
			log.info(memo);
		}
	}

	@Test
	public void testUpdate(){
//		Memo memo=Memo.builder().mno(100L).memoText("Update Test...").build();
//		memoRepository.save(memo);
		Long mno=100L;
		Optional<Memo> result=memoRepository.findById(mno); //Optional<Memo>사용하여 값이 없으면 null 출력,에러 출력x
		if (!result.isEmpty()){
			Memo memo=result.get();
			memo.changeMemoTest("Update Test");
			log.info(memoRepository.save(memo));
		}
	}


	@Test
	public void testDelete(){
		Long mno=100L;
		memoRepository.deleteById(mno);
	}

	//페이징 처리
	@Test
	public void testPageDefault(){
		Sort sort1=Sort.by("mno").ascending();
		Sort sort2=Sort.by("mno").descending();
		Sort sort3=Sort.by("memoText").ascending();
		Sort sortAll= sort2.and(sort3);

		Pageable pageable= PageRequest.of(0,10,sort1); //10 포함 x
		Page<Memo> result=memoRepository.findAll(pageable);
		log.info(result);
		System.out.println("------------------------");
		System.out.println("Total Pages : "+result.getTotalPages()); //총 페이지 수
		System.out.println("Total Count : "+result.getTotalElements()); //총 갯수
		System.out.println("Current Page : "+result.getNumber()); //현재 페이지 (0부터 시작)
		System.out.println("Page Size : "+result.getSize()); //현재 페이지 목록 갯수
		System.out.println("has next Page? : "+result.hasNext()); // 다음 페이지 존재 여부
		System.out.println("first page? : "+result.isFirst()); //시작 페이지 여부

		System.out.println("------------------------");
		for(Memo memo:result.getContent()){
			System.out.println(memo);
		}
	}


	//쿼리 메소드 테스트
	@Test
	public void testQueryMethod(){
		List<Memo> list=memoRepository.findByMnoBetweenOrderByMnoDesc(20L,30L);
		for(Memo memo:list){
			System.out.println(memo);
		}
	}
	@Test
	public void testQueryMethodWithPageable(){
		Pageable pageable=PageRequest.of(0,10,Sort.by("mno").descending());
		Page<Memo> result=memoRepository.findByMnoBetween(10L,50L,pageable);
		result.get().forEach(memo-> System.out.println(memo)); //10~50사이이지만  10개만 출력하므로 50부터 41까지만 출력
	}

	@Transactional
	@Commit
	@Test
	public void testDeleteQueryMethods(){
		memoRepository.deleteMemoByMnoLessThan(10L); //10보다 작은거 삭제
	}

}