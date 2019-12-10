package io.github.kings1990.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Rap2GeneratorWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(Rap2GeneratorWebApplication.class, args);
	}

}
