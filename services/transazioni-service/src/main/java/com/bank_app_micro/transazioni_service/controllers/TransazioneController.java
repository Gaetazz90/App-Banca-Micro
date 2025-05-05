package com.bank_app_micro.transazioni_service.controllers;

import com.bank_app_micro.transazioni_service.domain.dto.requests.TransazioneRequest;
import com.bank_app_micro.transazioni_service.domain.dto.responses.EntityIdResponse;
import com.bank_app_micro.transazioni_service.domain.entities.Transazione;
import com.bank_app_micro.transazioni_service.services.TransazioneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/transazioni")
public class TransazioneController {

    @Autowired
    private TransazioneService transazioneService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Transazione>> getAll(){
        return new ResponseEntity<>(transazioneService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Transazione> getById(@PathVariable Long id){
        return new ResponseEntity<>(transazioneService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/create/bonifico")
    public ResponseEntity<EntityIdResponse> createBonifico(@RequestBody @Valid TransazioneRequest request){
        return new ResponseEntity<>(transazioneService.createBonifico(request), HttpStatus.CREATED);
    }

    @PostMapping("/create/prelievo")
    public ResponseEntity<EntityIdResponse> createPrelievo(@RequestBody @Valid TransazioneRequest request){
        return new ResponseEntity<>(transazioneService.createPrelievo(request), HttpStatus.CREATED);
    }

    @PostMapping("/create/versamento")
    public ResponseEntity<EntityIdResponse> createVersamento(@RequestBody @Valid TransazioneRequest request){
        return new ResponseEntity<>(transazioneService.createVersamento(request), HttpStatus.CREATED);
    }

}
