package org.zerock.ex1.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
public class GuestDTO {
	private String name;
	private String id;
	private String pw;


	public void strPrint(){
		log.info("test Lombok");
	}
}
