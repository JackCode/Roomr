package com.jackcode.Roomr;

import com.jackcode.Roomr.backend.repository.RoomRepository;
import com.jackcode.Roomr.security.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableMongoRepositories(basePackageClasses = {RoomRepository.class, UserRepository.class})
public class RoomrApplication {

	public static void main(String[] args) { SpringApplication.run(RoomrApplication.class, args); }

}
