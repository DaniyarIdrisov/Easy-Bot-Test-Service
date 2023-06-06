package ru.daniyar.idrisov.testservice.exceptions;

import org.springframework.http.HttpStatus;

public class EntityConflictException extends ServiceException {

    public EntityConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public EntityConflictException(String message, Object... args) {
        super(message, HttpStatus.CONFLICT, args);
    }

}
