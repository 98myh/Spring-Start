package org.zerock.ex1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//client에게 json파일을 전송해준다
@RestController
public class SampleController {
	@GetMapping("/hello")
	public String[] hello(){
		return new String[]{"Hello","World"};
	}
}
