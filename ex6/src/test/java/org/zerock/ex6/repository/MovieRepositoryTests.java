package org.zerock.ex6.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex6.entity.Movie;
import org.zerock.ex6.entity.MovieImage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MovieRepositoryTests {
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieImageRepository movieImageRepository;

	@Test
	@Transactional
	@Commit
	public void insertMovies(){
		IntStream.rangeClosed(1,100).forEach(i->{
			Movie movie=Movie.builder()
					.title("Movie..."+i)
					.build();
			movieRepository.save(movie);

			int count=(int)(Math.random()*5)+1;
			for(int j=0;j<count;j++){
				MovieImage mi=MovieImage.builder()
						.uuid(UUID.randomUUID().toString())
						.movie(movie)
						.imgName("text"+j+".jpg")
						.build();
				movieImageRepository.save(mi);
			}
		});
	}

	@Test
	public void testListPage(){
		PageRequest pageRequest=PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"mno"));
		Page<Object[]> result=movieRepository.getListPage(pageRequest);
		for(Object[] obj : result.getContent()){
			System.out.println(Arrays.toString(obj));
		}
	}


	@Test
	public void getMovieWithAll(){
		List<Object[]> result=movieRepository.getMovieWithAll(94L);
		System.out.println(result);
		for(Object[] arr : result){
			System.out.println(Arrays.toString(arr));
		}
	}
}