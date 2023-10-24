package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
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

	//리스트 페이지로 이동
	@GetMapping("/list")
	public void guestbookList(PageRequestDTO pageRequestDTO, Model model){
		log.info("guestbookList..........");
		model.addAttribute("result",service.getList(pageRequestDTO));
	}

	//등록 페이지로 이동
	@GetMapping("/register")
	public void register(){
		log.info("register.....");

	}

	//등록하기
	@PostMapping("/register")
	public String registerPost(GuestbookDTO dto, RedirectAttributes ra){//글번호는 한번만 넘겨주기 때문에 Model 대신 RedirectAttributes 사용
		log.info("register.....");
		Long gno=service.register(dto);
		ra.addFlashAttribute("msg",gno+" 등록");
		return "redirect:/guestbook/list";

	}

	//게시판 상세보기로 이동,수정하기 이동
	@GetMapping({"/read","/modify"})
	public void read(Long gno,@ModelAttribute("requestDTO") PageRequestDTO dto,Model model){ //되돌아갈때 해당 페이지로 돌아 갈 수 있돌고 PageRequdstDTO 넘겨줌
		GuestbookDTO guestbookDTO=service.read(gno);
		model.addAttribute("dto",guestbookDTO);
	}

	//SSR로 작동되므로 POST,GET만 지원
	@PostMapping("/modify")
	public String modify(GuestbookDTO dto, RedirectAttributes ra,PageRequestDTO pageRequestDTO){
		log.info("modify...");
		service.modify(dto);
		ra.addAttribute("gno",dto.getGno());
		ra.addAttribute("page",pageRequestDTO.getPage());
		ra.addAttribute("type",pageRequestDTO.getType());
		ra.addAttribute("keyword",pageRequestDTO.getKeyword());
		ra.addFlashAttribute("msg",dto.getGno()+" 수정");
		return "redirect:/guestbook/read";
	}

	@PostMapping("/remove")
	public String remove(Long gno, RedirectAttributes ra, PageRequestDTO pageRequestDTO){

		log.info("remove...");
		service.remove(gno);
		if(service.getList(pageRequestDTO).getDtoList().isEmpty()&&pageRequestDTO.getPage()!=1){
			ra.addAttribute("page",pageRequestDTO.getPage()-1);
		}else{
			ra.addAttribute("page",pageRequestDTO.getPage());
		}
		ra.addAttribute("type",pageRequestDTO.getType());
		ra.addAttribute("keyword",pageRequestDTO.getKeyword());
		ra.addFlashAttribute("msg",gno+" 삭제");
		return "redirect:/guestbook/list";
	}

}
