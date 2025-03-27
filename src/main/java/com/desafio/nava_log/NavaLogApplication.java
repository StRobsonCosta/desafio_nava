package com.desafio.nava_log;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.desafio.nava_log")
@OpenAPIDefinition(info = @Info(title = "API de Logística", version = "v1", description = "API para gerenciar transportadoras", contact = @Contact(name = "Suporte", email = "suporte@empresa.com")))
public class NavaLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavaLogApplication.class, args);
	}
}
