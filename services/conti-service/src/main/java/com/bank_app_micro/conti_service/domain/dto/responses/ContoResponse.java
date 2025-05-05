package com.bank_app_micro.conti_service.domain.dto.responses;

import lombok.Builder;

@Builder
public record ContoResponse(Long id,
                            Double saldoDisponibile,
                            Double costoAnnuale,
                            Long intestatarioId) {
}
