package com.bank_app_micro.notifiche_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
(
		exclude ={org.springframework.ai.vectorstore.mongodb.autoconfigure.MongoDBAtlasVectorStoreAutoConfiguration.class}
)
public class NotificheServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificheServiceApplication.class, args);
	}

}
