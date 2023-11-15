package org.zerock.ex7.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.ex7.dto.ClubMemberDTO;
import org.zerock.ex7.security.dto.ClubAuthMemberDTO;
import org.zerock.ex7.service.ClubMemberService;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleController {
	private final ClubMemberService clubMemberService;

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

	@GetMapping("modify")
	public void modify(@AuthenticationPrincipal
					   ClubAuthMemberDTO clubAuthMemberDTO, Model model) {
		model.addAttribute("auth", clubAuthMemberDTO);
		List<String> roleNames = new ArrayList<>();
		clubAuthMemberDTO.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		model.addAttribute("roleNames", roleNames);
	}

	@PostMapping("modify")
	public String modifyForm(ClubMemberDTO clubMemberDTO, Model model) {
		log.info("ClubMemberDTO:" + clubMemberDTO);
		clubMemberService.updateClubMemberDTO(clubMemberDTO);
		return "redirect:/sample/all";
	}

}

