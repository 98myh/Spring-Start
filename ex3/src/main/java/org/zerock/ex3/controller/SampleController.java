package org.zerock.ex3.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex3.dto.SampleDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample") //리액트 라우터 개념
@Log4j2
public class SampleController {

	@GetMapping("/ex1") //resources파일의 sample파일에 있는 ex1.html파일을 불러옴
	public void ex1(){
		log.info("ex1-----------");
	}

	@GetMapping({"/ex2","/exlink"}) //resources파일의 sample파일에 있는 ex2.html파일을 불러옴
	public void ex2(Model model){
		//원래 service로 빼야하는 로직
		List<SampleDTO> list= IntStream.rangeClosed(1,20).asLongStream().mapToObj(i->{
			SampleDTO dto=SampleDTO.builder()
					.sno(i)
					.first("first...."+i)
					.last("last..."+i)
					.regTime(LocalDateTime.now())
					.build();
			return dto;
		}).collect(Collectors.toList());
		model.addAttribute("list",list);
	}

	@GetMapping({"/exInline"}) //{}가 있는경우는 아래의 똑같은 기능을 여러개의 url에서 사용할 경우
	public String exInline(RedirectAttributes ra){
		log.info("exInline............");
		SampleDTO dto=SampleDTO.builder()
				.sno(100L)
				.first("First...100")
				.last("Last...100")
				.regTime(LocalDateTime.now())
				.build();
		ra.addFlashAttribute("result","success");
		ra.addFlashAttribute("dto",dto); //redirect해주면 데이터 한번 넘겨줌,1회성
		ra.addAttribute("test","test"); //데이터가 계속 살아있음
		return "redirect:/sample/ex3"; // ex3으로 데이터 전달, 하지만 새로고침하면 데이터 사라짐(ex3에는 내용이 없기때문) -> request객체 이용해서 받을 수 있음
	}

	@GetMapping("/ex3")
	public void ex3(Model model,@RequestParam("test")String test) {
		model.addAttribute("test",test);//바로 ex3으로 접근시 에러 -> 받은 데이터가 없기 때문
		log.info("ex3..........");
	}

}
