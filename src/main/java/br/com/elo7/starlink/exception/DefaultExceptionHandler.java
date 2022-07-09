package br.com.elo7.starlink.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandler {

    Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ErrorInfo> applicationErrorHandler(HttpServletRequest req, ApplicationException e) {
        String message = getMessage(e);
        ErrorInfo errorInfo = new ErrorInfo(message);
        log.error(message, e);
        if (e.getStatus() != null) {
            return new ResponseEntity<>(errorInfo, e.getStatus());
        }
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationExceptions(HttpServletRequest req, MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ErrorInfo(errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorInfo> defaultErrorHandler(HttpServletRequest req, Exception e) {
        String message = "Ocorreu um erro ao executar a requisição";
        log.error(message, e);
        return new ResponseEntity<>(new ErrorInfo(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessage(Exception e) {
        String message = e.getMessage();
        if (message.isEmpty()) {
            message = "Ocorreu um erro ao executar a requisição";
        }
        return message;
    }
}