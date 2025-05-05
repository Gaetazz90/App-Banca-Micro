package com.bank_app_micro.conti_service.domain.dto.mappers;

import com.bank_app_micro.conti_service.domain.dto.responses.ContoResponse;
import com.bank_app_micro.conti_service.domain.entities.Conto;
import org.springframework.stereotype.Service;

@Service
public class ContoMapper {

    public ContoResponse fromContoToResponse(Conto conto){
        return ContoResponse.builder()
                .id(conto.getId())
                .saldoDisponibile(conto.getSaldoDisponibile())
                .costoAnnuale(conto.getCostoAnnuale())
                .intestatarioId(conto.getIntestatarioId())
                .build();
    }

}
