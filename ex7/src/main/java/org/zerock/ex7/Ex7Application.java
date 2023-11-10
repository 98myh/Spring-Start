package org.zerock.ex7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "org.zerock.ex7.repository")
public class Ex7Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex7Application.class, args);
	}

}
