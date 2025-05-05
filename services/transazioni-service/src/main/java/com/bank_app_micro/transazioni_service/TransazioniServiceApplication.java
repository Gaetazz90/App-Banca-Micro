package com.bank_app_micro.transazioni_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class TransazioniServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransazioniServiceApplication.class, args);
	}

}
