package ru.practicum.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.practicum.enums.ErrorStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
public class ApiError {
    @JsonFormat
    private String description;
    @JsonFormat
    private List[] errors;
    @JsonFormat
    private String message;
    @JsonFormat
    private String reason;
    @JsonFormat
    private ErrorStatus status;
    @JsonFormat
    private String timestamp = LocalDateTime.now().toString();


}
