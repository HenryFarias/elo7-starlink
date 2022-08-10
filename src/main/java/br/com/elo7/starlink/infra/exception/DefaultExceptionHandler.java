package br.com.elo7.starlink.infra.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandler {

    Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    public static final String REQUEST_ERROR_MESSAGE = "An error occurred while executing the request";

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ErrorInfo> applicationErrorHandler(ApplicationException e) {
        String message = e.getMessage();
        ErrorInfo errorInfo = new ErrorInfo(message);
        log.error(message, e);
        if (e.getStatus() != null) {
            return new ResponseEntity<>(errorInfo, e.getStatus());
        }
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ErrorInfo(errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorInfo> defaultErrorHandler(Exception e) {
        String message = REQUEST_ERROR_MESSAGE;
        log.error(message, e);
        return new ResponseEntity<>(new ErrorInfo(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}