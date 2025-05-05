package com.bank_app_micro.utenti_service.services;

import com.bank_app_micro.utenti_service.domain.dto.mappers.UtenteMapper;
import com.bank_app_micro.utenti_service.domain.dto.requests.InsertUtenteRequest;
import com.bank_app_micro.utenti_service.domain.dto.requests.UpdateUtenteRequest;
import com.bank_app_micro.utenti_service.domain.dto.responses.EntityIdResponse;
import com.bank_app_micro.utenti_service.domain.entities.Utente;
import com.bank_app_micro.utenti_service.domain.exceptions.MyEntityNotFoundException;
import com.bank_app_micro.utenti_service.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private UtenteMapper utenteMapper;
    @Autowired
    private ComuneService comuneService;

    public List<Utente> getAll(){
        return utenteRepository.findAll();
    }

    public Utente getById(Long id){
        return utenteRepository.findById(id).orElseThrow(()->
                new MyEntityNotFoundException("Utente con id: " + id + " non trovato"));
    }

    public EntityIdResponse insertUtente(InsertUtenteRequest request){
        Utente newUtente = utenteMapper.fromRequestToUtente(request);
        utenteRepository.save(newUtente);
        return EntityIdResponse.builder().id(newUtente.getId()).build();
    }

    public EntityIdResponse updateUtenteById(Long id, UpdateUtenteRequest request){
        Utente myUtente = getById(id);
        if (request.nome() != null) myUtente.setNome(request.nome());
        if (request.cognome()!= null) myUtente.setCognome(request.cognome());
        if (request.dataNascita() != null) myUtente.setDataNascita(request.dataNascita());
        if (request.codiceFiscale() != null) myUtente.setCodiceFiscale(request.codiceFiscale());
        if (request.email() != null) myUtente.setEmail(request.email());
        if (request.telefono() != null) myUtente.setTelefono(request.telefono());
        if (request.comuneId() != null) myUtente.setComuneResidenza(comuneService.getById(request.comuneId()));
        utenteRepository.save(myUtente);
        return EntityIdResponse.builder().id(myUtente.getId()).build();
    }

    public void deleteUtenteById(Long id){
        getById(id);
        utenteRepository.deleteById(id);
    }


}
