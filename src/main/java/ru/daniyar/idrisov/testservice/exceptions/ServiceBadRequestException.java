package ru.daniyar.idrisov.testservice.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceBadRequestException extends ServiceException {

    public ServiceBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ServiceBadRequestException(String message, Object... args) {
        super(message, HttpStatus.BAD_REQUEST, args);
    }

}
