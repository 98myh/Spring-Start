package org.zerock.ex5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "org.zerock.ex5.repository")
public class Ex5Application {

	public static void main(String[] args)
	{
		SpringApplication.run(Ex5Application.class, args);
	}
}
