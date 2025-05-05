package com.bank_app_micro.conti_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.bank_app_micro.conti_service.domain.dto.responses.IntestatarioResponse;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "utenti-service", url = "http://localhost:8070/app/utenti")
public interface UtenteClient {

    @GetMapping("/get/{id}")
    IntestatarioResponse getIntestatarioById(@PathVariable("id") Long id);

}
