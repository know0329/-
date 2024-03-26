package com.d208.giggyrank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GiggyrankApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiggyrankApplication.class, args);
	}

}
