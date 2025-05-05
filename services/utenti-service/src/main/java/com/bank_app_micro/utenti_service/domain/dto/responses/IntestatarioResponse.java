package com.bank_app_micro.utenti_service.domain.dto.responses;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record IntestatarioResponse( Long id,
                                    String nome,
                                    String cognome,
                                    String codiceFiscale,
                                    LocalDate dataNascita,
                                    Long comuneId,
                                    String email,
                                    String telefono) {
}
