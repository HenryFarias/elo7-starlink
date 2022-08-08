package br.com.elo7.starlink.infra.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private HttpStatus status;

    public ApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApplicationException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
