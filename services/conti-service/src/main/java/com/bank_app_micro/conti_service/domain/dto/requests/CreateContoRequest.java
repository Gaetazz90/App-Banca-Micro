package com.bank_app_micro.conti_service.domain.dto.requests;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;



public record CreateContoRequest
        (
                @NotNull
                Long intestatarioId,
                @NotNull
                @PositiveOrZero
                Double costoAnnuale,
                @NotNull
                @PositiveOrZero
                Double saldoDisponibile
        )
{

}
