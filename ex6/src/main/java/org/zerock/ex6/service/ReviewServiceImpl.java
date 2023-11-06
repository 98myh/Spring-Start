package org.zerock.ex6.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ex6.dto.ReviewDTO;
import org.zerock.ex6.entity.Movie;
import org.zerock.ex6.entity.Review;
import org.zerock.ex6.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	private final ReviewRepository reviewRepository;
	@Override
	public List<ReviewDTO> getListOfMovie(Long mno) {
		Movie movie= Movie.builder()
				.mno(mno)
				.build();
		List<Review>result=reviewRepository.findByMovie(movie);

		return result.stream().map(review->entityToDto(review)).collect(Collectors.toList());
	}

	@Override
	public Long register(ReviewDTO reviewDTO) {
		Review review=dtoToEntity(reviewDTO);
		reviewRepository.save(review);
		return review.getReviewnum();
	}

	@Override
	public void modify(ReviewDTO reviewDTO) {
		Optional<Review> result=reviewRepository.findById(reviewDTO.getReviewnum());
		if (result.isPresent()){
			Review review=result.get();
			review.changeGrade(reviewDTO.getGrade());
			review.changeText(reviewDTO.getText());
			reviewRepository.save(review);
		}


	}

	@Override
	public void remove(Long reviewNum) {
		reviewRepository.deleteById(reviewNum);
	}
}
