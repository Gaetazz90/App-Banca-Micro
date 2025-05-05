package com.bank_app_micro.utenti_service.domain.dto.mappers;

import com.bank_app_micro.utenti_service.domain.dto.requests.InsertUtenteRequest;
import com.bank_app_micro.utenti_service.domain.dto.responses.IntestatarioResponse;
import com.bank_app_micro.utenti_service.domain.entities.Utente;
import com.bank_app_micro.utenti_service.services.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteMapper {

    @Autowired
    private ComuneService comuneService;

    public Utente fromRequestToUtente(InsertUtenteRequest request){
        return Utente.builder()
                .nome(request.nome())
                .cognome(request.cognome())
                .dataNascita(request.dataNascita())
                .comuneResidenza(comuneService.getById(request.comuneId()))
                .codiceFiscale(request.codiceFiscale())
                .email(request.email())
                .telefono(request.telefono())
                .build();
    }

    public IntestatarioResponse fromUtenteToResponse(Utente utente){
        return IntestatarioResponse.builder()
                .id(utente.getId())
                .nome(utente.getNome())
                .cognome(utente.getCognome())
                .codiceFiscale(utente.getCodiceFiscale())
                .dataNascita(utente.getDataNascita())
                .comuneId(utente.getComuneResidenza().getId())
                .email(utente.getEmail())
                .telefono(utente.getTelefono())
                .build();
    }

}
