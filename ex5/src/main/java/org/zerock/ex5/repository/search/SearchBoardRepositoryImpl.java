package org.zerock.ex5.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.entity.QBoard;
import org.zerock.ex5.entity.QMember;
import org.zerock.ex5.entity.QReply;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//개발자가 원하는 대로 동작하는 Repository를 작성하기 위해 QuerydslRepositorySupport를 활용해서 직접 무언가를 구현할 때 사용
//일괄처리, sum,min,max 등의 값을 받아오기위해서 사용
@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{
	public SearchBoardRepositoryImpl(){
		super(Board.class);
	}
	@Override
	public Board search1() {
		log.info("search1..............");
		QBoard board=QBoard.board;
		QReply reply=QReply.reply;
		QMember member=QMember.member;

		JPQLQuery<Board> jpqlQuery=from(board);
		jpqlQuery.leftJoin(member).on(board.writer.eq(member));
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

		JPQLQuery<Tuple> tuple=jpqlQuery.select(board,member.email,reply.count());
//		jpqlQuery.select(board, member.email,reply.count()).groupBy(board);
		tuple.groupBy(board);

		log.info("==================");
		log.info(tuple);
		log.info("==================");
		List<Tuple> result=tuple.fetch();
		log.info("result : "+result);

		return null;
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
		log.info("search Page..............");

		//테이블
		QBoard board=QBoard.board;
		QReply reply=QReply.reply;
		QMember member=QMember.member;


		//조인
		JPQLQuery<Board> jpqlQuery=from(board);
		jpqlQuery.leftJoin(member).on(board.writer.eq(member));
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

		//select
		JPQLQuery<Tuple> tuple=jpqlQuery.select(board,member,reply.count());
//		jpqlQuery.select(board, member.email,reply.count()).groupBy(board);

		BooleanBuilder booleanBuilder=new BooleanBuilder();
		BooleanExpression expression=board.bno.gt(0L);//0보다 크다
		booleanBuilder.and(expression);
		if(type!=null){
			String[] typeArr=type.split("");
			BooleanBuilder conditionBuilder=new BooleanBuilder();
			//동적 쿼리 진행
			for(String t:typeArr){
				switch (t){
					case "t":
						conditionBuilder.or(board.title.contains(keyword));
						break;
					case "w":
						conditionBuilder.or(member.email.contains(keyword));
						break;
					case "c":
						conditionBuilder.or(board.content.contains(keyword));
						break;
				}
			}
			booleanBuilder.and(conditionBuilder);
		}
		//booleanBuilder 조건에 만족하는것
		tuple.where(booleanBuilder);

		//정렬 (Order by)
		Sort sort=pageable.getSort();
		sort.stream().forEach(i->{
			Order direction=i.isAscending()?Order.ASC:Order.DESC;
			String prop=i.getProperty();
			PathBuilder orderByExpression=new PathBuilder(Board.class,"board");
			tuple.orderBy(new OrderSpecifier<Comparable>(direction,orderByExpression.get(prop)));
		});

		tuple.groupBy(board);
		tuple.offset(pageable.getOffset());
		tuple.limit(pageable.getPageSize());

		List<Tuple> result=tuple.fetch();
		log.info(result);
		long count=tuple.fetchCount();
		log.info("COUNT : "+count);
		return new PageImpl<Object[]>(result.stream().map(t-> t.toArray()).collect(Collectors.toList()),pageable,count);
	}
}
