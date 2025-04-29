package com.slope.simple_login_recall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class SimpleRecallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRecallApplication.class, args);
	}

}
