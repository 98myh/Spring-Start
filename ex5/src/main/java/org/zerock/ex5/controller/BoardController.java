package org.zerock.ex5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
