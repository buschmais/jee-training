package com.buschmais.training.jee.application.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
