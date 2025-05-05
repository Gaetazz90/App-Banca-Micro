package com.bank_app_micro.transazioni_service.feign;

import com.bank_app_micro.transazioni_service.domain.dto.responses.IntestatarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "utenti-service", url = "http://localhost:8070/app/utenti")
public interface UtenteClient {

    @GetMapping("/get/{id}")
    IntestatarioResponse getIntestatarioById(@PathVariable("id") Long id);

}
