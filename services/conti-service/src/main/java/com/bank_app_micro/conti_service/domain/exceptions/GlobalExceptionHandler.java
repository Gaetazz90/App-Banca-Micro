package com.bank_app_micro.conti_service.domain.exceptions;

import com.bank_app_micro.conti_service.domain.dto.responses.ErrorResponse;
import com.bank_app_micro.conti_service.domain.exceptions.MyEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyEntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(MyEntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .exception(MyEntityNotFoundException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<ErrorResponse> handle(NotOwnerException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .exception(NotOwnerException.class.getSimpleName())
                        .message(exception.getMessage())
                        .build()
                );
    }

}
