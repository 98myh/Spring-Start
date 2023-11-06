package org.zerock.ex6.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex6.dto.MovieDTO;
import org.zerock.ex6.dto.MovieImageDTO;
import org.zerock.ex6.dto.PageRequestDTO;
import org.zerock.ex6.dto.PageResultDTO;
import org.zerock.ex6.entity.Movie;
import org.zerock.ex6.entity.MovieImage;
import org.zerock.ex6.repository.MovieImageRepository;
import org.zerock.ex6.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
	private final MovieRepository movieRepository;
	private final MovieImageRepository movieImageRepository;

	@Transactional
	@Override
	public Long register(MovieDTO movieDTO) {
		Map<String,Object> entityMap=dtoToEntity(movieDTO);
		Movie movie=(Movie) entityMap.get("movie");
		List<MovieImage> movieImageList=(List<MovieImage>) entityMap.get("imgList");

		movieRepository.save(movie);
		movieImageList.forEach(movieImage -> {
			movieImageRepository.save(movieImage);
		});
		return movie.getMno();
	}

	@Override
	public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
		Pageable pageable=pageRequestDTO.getPageable(Sort.by("mno").descending());
		Page<Object[]> result=movieRepository.getListPage(pageable);
		Function<Object[],MovieDTO> fn=(arr->entitiesToDTO(
				(Movie) arr[0],
				(List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
				(Double) arr[2],
				(Long) arr[3]

		));
		return new PageResultDTO<>(result,fn);
	}

	@Override
	public MovieDTO getMovie(Long mno) {
		List<Object[]> result=movieRepository.getMovieWithAll(mno);
		//Movie 가져오기
		Movie movie=(Movie)result.get(0)[0];
		List<MovieImage> movieImageDTOList=new ArrayList<>();

		//Movie 이미지 가져오기
		result.forEach(i->movieImageDTOList.add((MovieImage) i[1]));

		//평균 가져오기
		Double avg=(Double)result.get(0)[2];

		//댓글 개수 가져오기4
		Long reviewCount=(Long)result.get(0)[3];

		return entitiesToDTO(movie,movieImageDTOList,avg,reviewCount);
	}
}
