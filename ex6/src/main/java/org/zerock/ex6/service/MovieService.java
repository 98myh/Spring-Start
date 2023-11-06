package org.zerock.ex6.service;

import org.zerock.ex6.dto.MovieDTO;
import org.zerock.ex6.dto.MovieImageDTO;
import org.zerock.ex6.dto.PageRequestDTO;
import org.zerock.ex6.dto.PageResultDTO;
import org.zerock.ex6.entity.Movie;
import org.zerock.ex6.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface MovieService {
	Long register(MovieDTO movieDTO);

	PageResultDTO<MovieDTO,Object[]> getList(PageRequestDTO pageRequestDTO);
	MovieDTO getMovie(Long mno);


	default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
		Map<String, Object> entityMap = new HashMap<>();
		Movie movie = Movie.builder()
				.mno(movieDTO.getMno())
				.title(movieDTO.getTitle())
				.build();
		entityMap.put("movie", movie);
		List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
		if (imageDTOList != null && imageDTOList.size() > 0) {
			List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
				MovieImage movieImage = MovieImage.builder()
						.path(movieImageDTO.getPath())
						.imgName(movieImageDTO.getImgName())
						.uuid(movieImageDTO.getUuid())
						.movie(movie)
						.build();
				return movieImage;
			}).collect(Collectors.toList());
			entityMap.put("imgList", movieImageList);
		}
		return entityMap;
	}

	default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
		MovieDTO movieDTO = MovieDTO.builder()
				.mno(movie.getMno())
				.title(movie.getTitle())
				.regDate(movie.getRegDate())
				.modDate(movie.getModDate())
				.build();
		List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(
				new Function<MovieImage, MovieImageDTO>() {
					@Override
					public MovieImageDTO apply(MovieImage mi) {
						return MovieImageDTO.builder()
								.imgName(mi.getImgName())
								.path(mi.getPath())
								.uuid(mi.getUuid())
								.build();
					}
				}
		).collect(Collectors.toList());
		movieDTO.setImageDTOList(movieImageDTOList);
		movieDTO.setAvg(avg);
		movieDTO.setReviewCnt(reviewCnt.intValue());

		return movieDTO;
	}
}