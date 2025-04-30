package com.homeybites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineTiffinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineTiffinServiceApplication.class, args);
	}

}
