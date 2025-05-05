package com.bank_app_micro.transazioni_service.domain.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TransazioneRequest(
        @NotNull
        String tipoOperazione,
        @NotNull
        @Positive
        Double importo,
        @NotNull
        Long utenteId,
        @NotNull
        Long contoMittenteId,
        Long contoDestinatarioId
) {
}
