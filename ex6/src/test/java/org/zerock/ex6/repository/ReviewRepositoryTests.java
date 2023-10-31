package org.zerock.ex6.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex6.entity.Member;
import org.zerock.ex6.entity.Movie;
import org.zerock.ex6.entity.Review;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTests {

	@Autowired
	ReviewRepository reviewRepository;

	@Test
	public void insertReviews(){
		IntStream.rangeClosed(1,200).forEach(i->{
			//movie number
			Long mno=(long)(Math.random()*100)+1;

			//review number
			Long mid=(long)(Math.random()*100)+1;

			//Member
			Member member=Member.builder()
					.mid(mid)
					.build();

			Review review=Review.builder()
					.member(member)
					.movie(Movie.builder().mno(mno).build())
					.grade((int)(Math.random()*5)+1)
					.text("영화에 대한..."+i)
					.build();
			reviewRepository.save(review);
		});
	}

}