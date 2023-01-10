package ru.practicum.exceptions;

public class CrudException extends RuntimeException {
    String param;
    String value;

    public CrudException(String message, String param, String value) {
        super(message);
        this.param = param;
        this.value = value;
    }

    public CrudException(String message) {
        super(message);
    }

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }
}
