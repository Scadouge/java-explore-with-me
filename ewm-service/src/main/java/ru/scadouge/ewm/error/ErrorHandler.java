package ru.scadouge.ewm.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(NotFoundException e) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .reason("Требуемый объект не найден")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.warn(apiError.toString(), e);
        return apiError;
    }

    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflict(RuntimeException e) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT.toString())
                .reason("Условия для операции не выполнены")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.warn(apiError.toString(), e);
        return apiError;
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(Exception e) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Неверно составленный запрос")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.warn(apiError.toString(), e);
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleOtherExceptions(Throwable e) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .reason("Ошибка сервера")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.warn(apiError.toString(), e);
        return apiError;
    }
}
