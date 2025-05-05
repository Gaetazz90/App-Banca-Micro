package com.bank_app_micro.utenti_service.controllers;

import com.bank_app_micro.utenti_service.domain.dto.requests.InsertUtenteRequest;
import com.bank_app_micro.utenti_service.domain.dto.requests.UpdateUtenteRequest;
import com.bank_app_micro.utenti_service.domain.dto.responses.EntityIdResponse;
import com.bank_app_micro.utenti_service.domain.dto.responses.GenericResponse;
import com.bank_app_micro.utenti_service.domain.entities.Utente;
import com.bank_app_micro.utenti_service.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Utente>> getAll(){
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id){
        return new ResponseEntity<>(utenteService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<EntityIdResponse> insertUtente(@RequestBody @Valid InsertUtenteRequest request){
        return new ResponseEntity<>(utenteService.insertUtente(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> updateUtenteById(@PathVariable Long id, @RequestBody @Valid UpdateUtenteRequest request){
        return new ResponseEntity<>(utenteService.updateUtenteById(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteUtenteById(@PathVariable Long id){
        utenteService.deleteUtenteById(id);
        return new ResponseEntity<>(
                new GenericResponse("Utente con id: " + id + " cancellato con successo"), HttpStatus.OK);
    }

}
