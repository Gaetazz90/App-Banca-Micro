package com.bank_app_micro.utenti_service.repositories;

import com.bank_app_micro.utenti_service.domain.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
}
