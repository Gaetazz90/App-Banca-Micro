package com.bank_app_micro.conti_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ContiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContiServiceApplication.class, args);
	}

}
