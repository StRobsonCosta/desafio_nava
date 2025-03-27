package com.desafio.nava_log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.desafio.nava_log")
public class NavaLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavaLogApplication.class, args);
	}
}
