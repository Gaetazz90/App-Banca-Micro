package com.bank_app_micro.conti_service.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import java.time.LocalDate;

import org.hibernate.annotations.Check;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "conto_bancario")
public class Conto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "costo_annuale", nullable = false)
    @Check(constraints = "costo_annuale >= 0", name = "costo_conto_positive")
    private Double costoAnnuale;

    @Column(name = "saldo_disponibile",nullable = false)
    private Double saldoDisponibile;

    @Column(nullable = false, name = "data_sottoscrizione")
    private LocalDate dataSottoscrizione;

    @Column(name = "intestatario_id", nullable = false)
    private Long intestatarioId;

}
