package ru.scadouge.stats.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException e) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Неверно составленный запрос")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.warn(apiError.toString(), e);
        return apiError;
    }
}
