package com.bank_app_micro.transazioni_service.services;

import com.bank_app_micro.transazioni_service.domain.dto.requests.TransazioneRequest;
import com.bank_app_micro.transazioni_service.domain.dto.requests.UpdateSaldoRequest;
import com.bank_app_micro.transazioni_service.domain.dto.responses.ContoResponse;
import com.bank_app_micro.transazioni_service.domain.dto.responses.EntityIdResponse;
import com.bank_app_micro.transazioni_service.domain.dto.responses.IntestatarioResponse;
import com.bank_app_micro.transazioni_service.domain.entities.Transazione;
import com.bank_app_micro.transazioni_service.domain.enums.Operazione;
import com.bank_app_micro.transazioni_service.domain.exceptions.IllegalTransactionException;
import com.bank_app_micro.transazioni_service.domain.exceptions.MyEntityNotFoundException;
import com.bank_app_micro.transazioni_service.feign.ContoClient;
import com.bank_app_micro.transazioni_service.feign.UtenteClient;
import com.bank_app_micro.transazioni_service.kafka.producers.TransactionConfirmation;
import com.bank_app_micro.transazioni_service.kafka.producers.TransactionProducer;
import com.bank_app_micro.transazioni_service.repositories.TransazioneRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransazioneService {

    @Autowired
    private TransazioneRepository transazioneRepository;
    @Autowired
    private UtenteClient utenteClient;
    @Autowired
    private ContoClient contoClient;
    @Autowired
    private TransactionProducer transactionProducer;


    public List<Transazione> getAll(){
        return transazioneRepository.findAll();
    }

    public Transazione getById(Long id){
        return transazioneRepository.findById(id).orElseThrow(()
                -> new MyEntityNotFoundException("Transazione con id: " + id + " non trovata"));
    }

    @Transactional
    public EntityIdResponse createBonifico(TransazioneRequest request){

        IntestatarioResponse utenteIntestatario;
        try
        {
           utenteIntestatario = utenteClient.getIntestatarioById(request.utenteId());
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("L'utente non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }

        ContoResponse contoMittente;
        try
        {
           contoMittente = contoClient.getContoById(request.contoMittenteId());
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("Il conto mittente non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }

        ContoResponse contoDestinatario;
            try
            {
                contoDestinatario = contoClient.getContoById(request.contoDestinatarioId());
            } catch (FeignException ex)
            {
                int status = ex.status();

                if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                    throw new MyEntityNotFoundException("Il conto destinatario non esiste");
                }

                throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
            }

        if(!contoMittente.intestatarioId().equals(utenteIntestatario.id())){
            throw new IllegalTransactionException("L'utente che vuole fare il bonifico non è l'intestatario del conto");
        }
        if(contoMittente.saldoDisponibile() < request.importo()){
            throw new IllegalTransactionException("Saldo insufficiente per eseguire la transazione");
        }

        contoClient.aggiornaSaldo(
                contoMittente.id(),
                new UpdateSaldoRequest(contoMittente.saldoDisponibile() - request.importo())
        );

        contoClient.aggiornaSaldo(
                contoDestinatario.id(),
                new UpdateSaldoRequest(contoDestinatario.saldoDisponibile() + request.importo())
        );

        Transazione transazione = Transazione
                .builder()
                .tipo(Operazione.BONIFICO)
                .importo(request.importo())
                .data(LocalDateTime.now())
                .utenteId(request.utenteId())
                .contoMittente(request.contoMittenteId())
                .contoDestinatario(request.contoDestinatarioId())
                .build();
        transazioneRepository.save(transazione);

        //Creazione notifica
        TransactionConfirmation notificaTransazione = TransactionConfirmation.builder()
                .transazioneId(transazione.getId())
                .utenteId(transazione.getUtenteId())
                .timestamp(LocalDateTime.now())
                .build();
        //Invio notifica
        try {
            transactionProducer.inviaNotificaTransazione(notificaTransazione);
        } catch (Exception ex) {
            throw new RuntimeException("Errore durante l'invio del messaggio a Kafka: " + ex.getMessage());
        }

        return EntityIdResponse.builder().id(transazione.getId()).build();

    }

    @Transactional
    public EntityIdResponse createVersamento(TransazioneRequest request){

        IntestatarioResponse utenteIntestatario;
        try
        {
            utenteIntestatario = utenteClient.getIntestatarioById(request.utenteId());
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("L'utente non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }

        ContoResponse contoMittente;
        try
        {
            contoMittente = contoClient.getContoById(request.contoMittenteId());
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("Il conto mittente non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }


        if(!contoMittente.intestatarioId().equals(utenteIntestatario.id())){
            throw new IllegalTransactionException("L'utente che vuole fare il versamento non è l'intestatario del conto");
        }

        contoClient.aggiornaSaldo(
                contoMittente.id(),
                new UpdateSaldoRequest(contoMittente.saldoDisponibile() + request.importo())
        );

        Transazione transazione = Transazione
                .builder()
                .tipo(Operazione.VERSAMENTO)
                .importo(request.importo())
                .data(LocalDateTime.now())
                .utenteId(request.utenteId())
                .contoMittente(request.contoMittenteId())
                .contoDestinatario(request.contoDestinatarioId())
                .build();
        transazioneRepository.save(transazione);

        //Creazione notifica
        TransactionConfirmation notificaTransazione = TransactionConfirmation.builder()
                .transazioneId(transazione.getId())
                .utenteId(transazione.getUtenteId())
                .timestamp(LocalDateTime.now())
                .build();
        //Invio notifica
        try {
            transactionProducer.inviaNotificaTransazione(notificaTransazione);
        } catch (Exception ex) {
            throw new RuntimeException("Errore durante l'invio del messaggio a Kafka: " + ex.getMessage());
        }

        return EntityIdResponse.builder().id(transazione.getId()).build();

    }

    @Transactional
    public EntityIdResponse createPrelievo(TransazioneRequest request){

        IntestatarioResponse utenteIntestatario;
        try
        {
            utenteIntestatario = utenteClient.getIntestatarioById(request.utenteId());
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("L'utente non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }

        ContoResponse contoMittente;
        try
        {
            contoMittente = contoClient.getContoById(request.contoMittenteId());
        } catch (FeignException ex)
        {
            int status = ex.status();

            if (status == HttpStatus.NOT_FOUND.value() || status == HttpStatus.BAD_REQUEST.value()) {
                throw new MyEntityNotFoundException("Il conto mittente non esiste");
            }

            throw new RuntimeException("Errore di comunicazione con il microservizio dipendenti: " + ex.getMessage());
        }


        if(!contoMittente.intestatarioId().equals(utenteIntestatario.id())){
            throw new IllegalTransactionException("L'utente che vuole fare il prelievo non è l'intestatario del conto");
        }
        if(contoMittente.saldoDisponibile() < request.importo()){
            throw new IllegalTransactionException("Saldo insufficiente per eseguire la transazione");
        }

        contoClient.aggiornaSaldo(
                contoMittente.id(),
                new UpdateSaldoRequest(contoMittente.saldoDisponibile() - request.importo())
        );

        Transazione transazione = Transazione
                .builder()
                .tipo(Operazione.PRELIEVO)
                .importo(request.importo())
                .data(LocalDateTime.now())
                .utenteId(request.utenteId())
                .contoMittente(request.contoMittenteId())
                .contoDestinatario(request.contoDestinatarioId())
                .build();
        transazioneRepository.save(transazione);

        //Creazione notifica
        TransactionConfirmation notificaTransazione = TransactionConfirmation.builder()
                .transazioneId(transazione.getId())
                .utenteId(transazione.getUtenteId())
                .timestamp(LocalDateTime.now())
                .build();
        //Invio notifica
        try {
            transactionProducer.inviaNotificaTransazione(notificaTransazione);
        } catch (Exception ex) {
            throw new RuntimeException("Errore durante l'invio del messaggio a Kafka: " + ex.getMessage());
        }

        return EntityIdResponse.builder().id(transazione.getId()).build();

    }

}
