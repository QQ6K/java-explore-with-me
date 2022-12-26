package ru.practicum.exceptions;

import ru.practicum.enums.ErrorStatus;

import java.util.List;

public class ApiError {
    private String description;
    private List[] errors;
    private String message;
    private String reason;
    private ErrorStatus status;
    private String timestamp;
}
