package com.jackcode.Roomr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class RoomrApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomrApplication.class, args);
	}

}
