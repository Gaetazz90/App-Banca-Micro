package com.bank_app_micro.utenti_service.domain.dto.requests;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateUtenteRequest(
                                  String nome,
                                  String cognome,
                                  @Email(message = "Email non valida")
                                  String email,
                                  @Pattern(regexp = "^\\+?[0-9]+$", message = "Telefono non valido")
                                  String telefono,
                                  @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-EHLMPR-T][0-9]{2}[A-Z][0-9]{3}[A-Z]$",
                                          message = "Codice fiscale non valido")
                                  String codiceFiscale,
                                  @Past(message = "La data di nascita deve essere nel passato")
                                  LocalDate dataNascita,
                                  Long comuneId) {
}
