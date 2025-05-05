package com.bank_app_micro.utenti_service.domain.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record InsertUtenteRequest
        (
                @NotBlank(message = "Il nome non può essere blank o null")
                String nome,
                @NotBlank(message = "Il cognome non può essere blank o null")
                String cognome,
                @Email(message = "Email non valida")
                @NotBlank
                String email,
                @Pattern(regexp = "^\\+?[0-9]+$", message = "Telefono non valido")
                @NotBlank
                String telefono,
                @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-EHLMPR-T][0-9]{2}[A-Z][0-9]{3}[A-Z]$",
                         message = "Codice fiscale non valido")
                @NotBlank
                String codiceFiscale,
                @Past(message = "La data di nascita deve essere nel passato")
                @NotNull
                LocalDate dataNascita,
                @NotNull(message = "il comune deve essere presente")
                Long comuneId
        )
{

}
