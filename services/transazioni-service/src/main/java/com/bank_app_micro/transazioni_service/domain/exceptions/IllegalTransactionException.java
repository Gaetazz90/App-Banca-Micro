package com.bank_app_micro.transazioni_service.domain.exceptions;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String message) {
        super(message);
    }
}
