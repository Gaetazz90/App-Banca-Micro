package com.bank_app_micro.utenti_service.domain.dto.responses;

import lombok.Builder;

@Builder
public record ErrorResponse(String exception, String message) {
}
