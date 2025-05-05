package com.bank_app_micro.conti_service.controllers;

import com.bank_app_micro.conti_service.domain.dto.requests.CreateContoRequest;
import com.bank_app_micro.conti_service.domain.dto.requests.UpdateContoRequest;
import com.bank_app_micro.conti_service.domain.dto.requests.UpdateSaldoRequest;
import com.bank_app_micro.conti_service.domain.dto.responses.EntityIdResponse;
import com.bank_app_micro.conti_service.domain.dto.responses.GenericResponse;
import com.bank_app_micro.conti_service.domain.entities.Conto;
import com.bank_app_micro.conti_service.services.ContoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/conti")
public class ContoController {

    @Autowired
    private ContoService contoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Conto> getById(@PathVariable Long id){
        return new ResponseEntity<>(contoService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Conto>> getAll(){
        return new ResponseEntity<>(contoService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> createConto(@RequestBody @Valid CreateContoRequest request){
        return new ResponseEntity<>(contoService.createConto(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/conto/{contoId}/intestatario/{intestatarioId}")
    public ResponseEntity<EntityIdResponse> updateConto(@PathVariable Long contoId, @PathVariable Long intestatarioId,@RequestBody @Valid UpdateContoRequest request){
        return new ResponseEntity<>(contoService.updateById(contoId, intestatarioId, request), HttpStatus.CREATED);
    }

    @PutMapping("/update/conto/{contoId}/saldo")
    public ResponseEntity<Void> aggiornaSaldo(@PathVariable Long contoId, @RequestBody UpdateSaldoRequest request) {
        contoService.aggiornaSaldo(contoId, request);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/conto/{contoId}/intestatario/{intestatarioId}")
    public ResponseEntity<GenericResponse> deleteConto(@PathVariable Long contoId, @PathVariable Long intestatarioId){
        contoService.deleteById(contoId, intestatarioId);
        return new ResponseEntity<>(new GenericResponse("Conto con id: " + contoId + " cancellato con successo"), HttpStatus.OK);
    }


}
