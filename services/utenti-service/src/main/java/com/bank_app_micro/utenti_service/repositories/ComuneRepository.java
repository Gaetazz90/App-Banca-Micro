package com.bank_app_micro.utenti_service.repositories;

import com.bank_app_micro.utenti_service.domain.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComuneRepository  extends JpaRepository<Comune, Long> {
}
