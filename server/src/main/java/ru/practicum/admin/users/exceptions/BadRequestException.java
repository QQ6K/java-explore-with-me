package ru.practicum.admin.users.exceptions;

public class BadRequestException extends CrudException {
    public BadRequestException(String message) {
        super(message);
    }
}

