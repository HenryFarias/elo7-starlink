package br.com.elo7.sonda.candidato.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

class ErrorInfo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}