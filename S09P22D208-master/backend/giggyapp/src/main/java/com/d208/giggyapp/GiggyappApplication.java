package com.d208.giggyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GiggyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiggyappApplication.class, args);
	}

}
