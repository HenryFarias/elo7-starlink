package br.com.elo7.starlink.infra.exception;

public class ErrorInfo {

    private String message;

    public ErrorInfo() {}

    public ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}