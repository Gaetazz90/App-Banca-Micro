package com.bank_app_micro.transazioni_service.kafka.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic transactionTopic() {
        return TopicBuilder
                .name("transazioni-topic")
                .build();
    }

}
