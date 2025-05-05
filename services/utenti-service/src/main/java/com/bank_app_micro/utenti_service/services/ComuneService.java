package com.bank_app_micro.utenti_service.services;

import com.bank_app_micro.utenti_service.domain.entities.Comune;
import com.bank_app_micro.utenti_service.domain.exceptions.MyEntityNotFoundException;
import com.bank_app_micro.utenti_service.repositories.ComuneRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComuneService {

    @Autowired
    private ComuneRepository comuneRepository;

    public Comune getById(Long id){
        return comuneRepository.findById(id).orElseThrow(() ->
                new MyEntityNotFoundException("Comune con id: " + id + " non trovato"));
    }

}
