package ru.practicum.exceptions;

public class BadRequestException extends CrudException {
    public BadRequestException(String message) {
        super(message);
    }
}

