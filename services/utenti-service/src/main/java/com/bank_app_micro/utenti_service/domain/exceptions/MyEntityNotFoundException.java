package com.bank_app_micro.utenti_service.domain.exceptions;

public class MyEntityNotFoundException extends RuntimeException {
    public MyEntityNotFoundException(String message) {
        super(message);
    }
}
