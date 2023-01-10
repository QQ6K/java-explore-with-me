package ru.practicum.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ru.practicum.enums.ErrorStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({WrongObjectException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> wrongException(WrongObjectException e) {
        log.error(e.getMessage());
        return Map.of("error", e.getMessage());
    }

   /* @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> notAvailableException(BadRequestException e) {
        log.error(e.getMessage());
        return Map.of("Ошибка:", e.getMessage());
}*/

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badRequestException(BadRequestException e) {
        log.error(e.getMessage());
        ApiError apiError = new ApiError("Ошибка",
                null, e.getMessage(),
                "Причина ошибки",
                BAD_REQUEST,
                LocalDateTime.now().format(formatter).toString());
        return objectMapper.convertValue(apiError, Map.class);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> conflictException(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        ApiError apiError = new ApiError("Запрос приводит к нарушению целостности данных",
                null, e.getMessage(),
                "Integrity constraint has been violated",
                CONFLICT,
                LocalDateTime.now().format(formatter).toString());
        return objectMapper.convertValue(apiError, Map.class);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(NotFoundException e) {
        ApiError apiError = new ApiError("Объект не найден",
                null, e.getMessage(),
                "The required object was not found.",
                NOT_FOUND,
                LocalDateTime.now().format(formatter).toString());
        return objectMapper.convertValue(apiError, Map.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> validationError(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError("Объект не найден",
                null, e.getMessage(),
                "Неверное тело запроса",
                BAD_REQUEST,
                LocalDateTime.now().format(formatter).toString());
        return objectMapper.convertValue(apiError, Map.class);
    }
}
