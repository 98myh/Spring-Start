package org.zerock.ex5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.ex5.dto.ReplyDTO;
import org.zerock.ex5.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService replyService;

	@GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {
		log.info("replies/board/bno: "+bno);
		return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
	}
	@PostMapping({"","/"})
	public ResponseEntity<Long> register(@RequestBody ReplyDTO dto){
		log.info("reply dto: "+dto);
		Long rno=replyService.register(dto);
		return new ResponseEntity<>(rno,HttpStatus.OK);
	}
	@PutMapping("/{rno}")
	public ResponseEntity<Long> modify(@RequestBody ReplyDTO dto) {
		log.info("reply dto: " + dto);
		replyService.modify(dto);
		return new ResponseEntity<>(dto.getRno(), HttpStatus.OK);
	}

	@DeleteMapping("/{rno}")
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
		log.info("reply rno: " + rno);
		replyService.remove(rno);
		return new ResponseEntity<>(rno+"", HttpStatus.OK);
	}
}