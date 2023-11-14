package org.zerock.ex7.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.ex7.security.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

	@GetMapping("/all")
	public void exAll(){

	}

	@GetMapping("/member")
	public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO, Model model){
		log.info(clubAuthMemberDTO);
		model.addAttribute("principal",clubAuthMemberDTO);
	}

	@GetMapping("/admin")
	public void exAdmin(){

	}

}

