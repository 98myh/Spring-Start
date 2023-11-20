package org.zerock.ex8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex8Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex8Application.class, args);
		System.out.println("localhost:8080/ex8");
	}

}
