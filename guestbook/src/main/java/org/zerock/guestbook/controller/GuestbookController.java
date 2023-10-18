package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {
	private final GuestbookService service; //final 붙여야 순환참조 X
	@GetMapping("/")
	public String guestbookIndex(){
		log.info("guestbookIndex..........");
		return "redirect:/guestbook/list";
	}
	@GetMapping("/list")
	public void guestbookList(PageRequestDTO pageRequestDTO, Model model){
		log.info("guestbookList..........");
		model.addAttribute("result",service.getList(pageRequestDTO));
	}

	@GetMapping("/register")
	public void register(){
		log.info("register.....");

	}
	@PostMapping("/register")
	public String registerPost(GuestbookDTO dto, RedirectAttributes ra){//글번호는 한번만 넘겨주기 때문에 Model 대신 RedirectAttributes 사용
		log.info("register.....");
		Long gno=service.register(dto);
		ra.addFlashAttribute("msg",gno);
		return "redirect:/guestbook/list";

	}
}
