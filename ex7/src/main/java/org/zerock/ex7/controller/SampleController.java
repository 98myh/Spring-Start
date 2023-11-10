package org.zerock.ex7.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

	@GetMapping("/all")
	public void exAll(){

	}

	@GetMapping("/member")
	public void exMember(){

	}

	@GetMapping("/admin")
	public void exAdmin(){

	}

}
