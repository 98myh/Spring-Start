package org.zerock.ex6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.ex6.dto.ReviewDTO;
import org.zerock.ex6.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/{mno}/all")
	public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
		List<ReviewDTO> reviewDTOList=reviewService.getListOfMovie(mno);
		return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
	}

	@PostMapping("/{mno}")
	public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO){
		Long reviewnum=reviewService.register(reviewDTO);
		return new ResponseEntity<>(reviewnum,HttpStatus.OK);
	}

	@PutMapping("/{mno}/{reviewnum}")
	public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum,@RequestBody ReviewDTO reviewDTO){
		reviewService.modify(reviewDTO);
		return new ResponseEntity<>(reviewnum,HttpStatus.OK);
	}

	@DeleteMapping("/{mno}/{reviewnum}")
	public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum){
		reviewService.remove(reviewnum);
		return new ResponseEntity<>(reviewnum,HttpStatus.OK);
	}
}
