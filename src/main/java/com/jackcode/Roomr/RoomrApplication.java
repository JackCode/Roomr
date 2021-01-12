package com.jackcode.Roomr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.devtools.restart.classloader.RestartClassLoader;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RoomrApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomrApplication.class, args);
	}

}
