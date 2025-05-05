package com.bank_app_micro.conti_service.services;

import com.bank_app_micro.conti_service.domain.dto.requests.CreateContoRequest;
import com.bank_app_micro.conti_service.domain.dto.requests.UpdateContoRequest;
import com.bank_app_micro.conti_service.domain.dto.requests.UpdateSaldoRequest;
import com.bank_app_micro.conti_service.domain.dto.responses.EntityIdResponse;
import com.bank_app_micro.conti_service.domain.entities.Conto;
import com.bank_app_micro.conti_service.domain.exceptions.MyEntityNotFoundException;
import com.bank_app_micro.conti_service.domain.exceptions.NotOwnerException;
import com.bank_app_micro.conti_service.feign.UtenteClient;
import com.bank_app_micro.conti_service.repositories.ContoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import feign.FeignException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContoService {

    @Autowired
    private ContoRepository contoRepository;
    @Autowired
    private UtenteClient utenteClient;

    public Conto getById(Long id){
        return contoRepository.findById(id).orElseThrow(() ->
                new MyEntityNotFoundException("Conto con id: " + id + " non trovato"));
    }

    public List<Conto> getAll(){
        return contoRepository.findAll();
    }

    public EntityIdResponse createConto(@RequestBody CreateContoRequest request){

        try
        {
            utenteClient.getIntestatarioById(request.intestatarioId());
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("L'utente che vuole aprire il conto non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }

        Conto newConto = Conto.builder()
                .intestatarioId(request.intestatarioId())
                .costoAnnuale(request.costoAnnuale())
                .saldoDisponibile(request.saldoDisponibile())
                .dataSottoscrizione(LocalDate.now())
                .build();

        contoRepository.save(newConto);
        return EntityIdResponse.builder().id(newConto.getId()).build();

    }

    public EntityIdResponse updateById(Long contoId, Long intestatarioId, UpdateContoRequest request){
        Conto conto = getById(contoId);

        try
        {
            utenteClient.getIntestatarioById(intestatarioId);
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("L'utente che vuole modificare il conto non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }

        if(!conto.getIntestatarioId().equals(intestatarioId)){
            throw new NotOwnerException("L'utente con id: " + intestatarioId + " non è l'intestatario del conto");
        }

        if(request.intestatarioId() != null) conto.setIntestatarioId(request.intestatarioId());
        if(request.costoAnnuale() != null) conto.setCostoAnnuale(request.costoAnnuale());

        contoRepository.save(conto);
        return EntityIdResponse.builder().id(contoId).build();
    }

    public void deleteById(Long contoId, Long intestatarioId){
        Conto conto = getById(contoId);

        try
        {
            utenteClient.getIntestatarioById(intestatarioId);
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("L'utente che vuole aprire il conto non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }

        if(!conto.getIntestatarioId().equals(intestatarioId)){
            throw new NotOwnerException("L'utente con id: " + intestatarioId + " non è l'intestatario del conto");
        }

        contoRepository.deleteById(contoId);
    }

    public void aggiornaSaldo(Long contoId, UpdateSaldoRequest request) {
        Conto conto = getById(contoId);
        conto.setSaldoDisponibile(request.saldoDisponibile());
        contoRepository.save(conto);
    }


}
