package com.bank_app_micro.transazioni_service.feign;

import com.bank_app_micro.transazioni_service.domain.dto.requests.UpdateSaldoRequest;
import com.bank_app_micro.transazioni_service.domain.dto.responses.ContoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "conti-service", url = "http://localhost:8071/app/conti")
public interface ContoClient {

    @GetMapping("/get/{id}")
    ContoResponse getContoById(@PathVariable("id") Long id);

    @PutMapping("/update/conto/{contoId}/saldo")
    void aggiornaSaldo(@PathVariable("contoId") Long id, @RequestBody UpdateSaldoRequest request);


}
