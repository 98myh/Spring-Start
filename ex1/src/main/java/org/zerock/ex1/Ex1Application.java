package org.zerock.ex1;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zerock.ex1.dto.GuestDTO;

@Log4j2
@SpringBootApplication
public class Ex1Application {
	public static void main(String[] args) {
		SpringApplication.run(Ex1Application.class, args);

		GuestDTO dto=new GuestDTO();
		dto.setName("a");
		System.out.println(dto.getName());
		dto.strPrint();

		log.info(">>",dto.getName());
		System.out.println("http://localhost:8080/ex1");
	}
}
