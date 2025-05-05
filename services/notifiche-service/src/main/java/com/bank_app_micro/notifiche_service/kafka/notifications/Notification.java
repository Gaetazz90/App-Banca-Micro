package com.bank_app_micro.notifiche_service.kafka.notifications;

import com.bank_app_micro.notifiche_service.kafka.consumers.TransactionConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Notification {

    @Id
    private String id;
    private TransactionConfirmation transactionConfirmation;
    private LocalDateTime notificationTime;
    private NotificationType notificationType;

}
