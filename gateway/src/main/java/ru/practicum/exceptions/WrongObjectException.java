package ru.practicum.exceptions;

public class WrongObjectException extends CrudException {
    public WrongObjectException(String message) {
        super(message);
    }
}
