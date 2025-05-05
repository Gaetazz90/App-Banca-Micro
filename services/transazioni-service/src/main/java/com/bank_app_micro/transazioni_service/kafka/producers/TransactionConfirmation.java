package com.bank_app_micro.transazioni_service.kafka.producers;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransactionConfirmation(Long transazioneId,
                                      Long utenteId,
//                                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                                      LocalDateTime timestamp) {
}
