package com.bank_app_micro.conti_service.repositories;

import com.bank_app_micro.conti_service.domain.entities.Conto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContoRepository extends JpaRepository<Conto, Long> {
}
