package com.bank_app_micro.transazioni_service.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    @Autowired
    private KafkaTemplate<String, TransactionConfirmation> kafkaTemplate;

    public void inviaNotificaTransazione(TransactionConfirmation transactionConfirmation) {
        Message<TransactionConfirmation> message = MessageBuilder
                .withPayload(transactionConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "transazioni-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
