package com.bank_app_micro.transazioni_service.repositories;

import com.bank_app_micro.transazioni_service.domain.entities.Transazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Long> {
}
