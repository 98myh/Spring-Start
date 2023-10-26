package org.zerock.ex5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex5.dto.BoardDTO;
import org.zerock.ex5.dto.PageRequestDTO;
import org.zerock.ex5.entity.Board;
import org.zerock.ex5.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@GetMapping({"","/"})
	public String boardList(){

		return "redirect:/board/list";
	}
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model){
		log.info("list..........., pageRequestDTO : "+pageRequestDTO);
		model.addAttribute("result",boardService.getList(pageRequestDTO));
	}

	@GetMapping("/register")
	public void register(){
		log.info("register get....");
	}

	@PostMapping("/register")
	public String registerPost(RedirectAttributes ra, BoardDTO dto) {
		log.info("registerPost: " + dto);
		Long bno = boardService.register(dto);
		if (bno != -1L) {
			ra.addAttribute("msg", bno + " 등록");
			return "redirect:/board/list";
		}else {
			return "redirect:/board/register";
		}
	}

	//글 상세 페이지 이동 및 수정페이지 이동
	@GetMapping({"/read","/modify"})
	public void read(@ModelAttribute("requestDTO")PageRequestDTO pageRequestDTO, Long bno, Model model){
		BoardDTO dto= boardService.get(bno);
		model.addAttribute("dto",dto);
	}


	//수정
	@PostMapping("/modify")
	public String modifyPost(BoardDTO dto, RedirectAttributes ra, PageRequestDTO pageRequestDTO){
		boardService.modify(dto);
		ra.addAttribute("bno",dto.getBno());
		ra.addAttribute("page",pageRequestDTO.getPage());
		ra.addAttribute("type",pageRequestDTO.getType());
		ra.addAttribute("keyword",pageRequestDTO.getKeyword());
		ra.addFlashAttribute("msg",dto.getBno()+" 수정");
		return "redirect:/board/read";
	}
	//삭제
	@PostMapping("/remove")
	public String remove(Long bno,RedirectAttributes ra,PageRequestDTO pageRequestDTO){
		boardService.removeWithReplies(bno);

		if(boardService.getList(pageRequestDTO).getDtoList().isEmpty()&&pageRequestDTO.getPage()!=1){
			ra.addAttribute("page",pageRequestDTO.getPage()-1);
		}else{
			ra.addAttribute("page",pageRequestDTO.getPage());
		}

		ra.addAttribute("type",pageRequestDTO.getType());
		ra.addAttribute("keyword",pageRequestDTO.getKeyword());
		ra.addFlashAttribute("msg",bno+" 삭제");
		return "redirect:/board/list";
	}

}
