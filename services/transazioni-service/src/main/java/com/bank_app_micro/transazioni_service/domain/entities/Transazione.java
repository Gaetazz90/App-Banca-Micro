package com.bank_app_micro.transazioni_service.domain.entities;

import com.bank_app_micro.transazioni_service.domain.enums.Operazione;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "transazione")
public class Transazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "tipo_operazione", nullable = false)
    private Operazione tipo;

    @Column(nullable = false)
    @Check(constraints = "importo > 0", name = "check_importo_positive")
    private Double importo;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(name = "utente_id", nullable = false)
    private Long utenteId;

    @Column(name = "conto_mittente", nullable = false)
    private Long contoMittente;

    @Column(name = "conto_destinatario")
    private Long contoDestinatario;

}
