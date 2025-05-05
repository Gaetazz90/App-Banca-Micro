package com.bank_app_micro.notifiche_service.kafka.consumers;

import com.bank_app_micro.notifiche_service.kafka.notifications.Notification;
import com.bank_app_micro.notifiche_service.kafka.notifications.NotificationRepository;
import com.bank_app_micro.notifiche_service.kafka.notifications.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "transazioni-topic", groupId = "transazioni-group")
    public void consumeTransactionNotification(TransactionConfirmation transactionConfirmation) {

        Notification newNotification = Notification
                        .builder()
                        .notificationType(NotificationType.TRANSAZIONE)
                        .notificationTime(LocalDateTime.now())
                        .transactionConfirmation(transactionConfirmation)
                        .build();

        notificationRepository.save(newNotification);

        System.out.println("✅ Transazione avvenuta con successo!");
        // TODO inviare la email di conferma dell'ordine
    }

}
