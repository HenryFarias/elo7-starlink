package br.com.elo7.starlink.exception;

import br.com.elo7.starlink.infra.exception.ApplicationException;
import br.com.elo7.starlink.infra.exception.DefaultExceptionHandler;
import br.com.elo7.starlink.infra.exception.ErrorInfo;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultExceptionHandlerTest {

    @Test
    public void should_call_application_exception_handler_success() {
        var errorMessage = "Error";
        var exceptionHandler = new DefaultExceptionHandler();
        var applicationException = new ApplicationException(errorMessage, HttpStatus.BAD_REQUEST);
        var expectedError = new ErrorInfo(errorMessage);

        var response = exceptionHandler.applicationErrorHandler(applicationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedError.getMessage(), response.getBody().getMessage());
    }

    @Test
    public void should_call_application_exception_handler_without_status_and_return_with_internal_server_error_success() {
        var errorMessage = "Error";
        var exceptionHandler = new DefaultExceptionHandler();
        var applicationException = new ApplicationException(errorMessage);
        var expectedError = new ErrorInfo(errorMessage);

        var response = exceptionHandler.applicationErrorHandler(applicationException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedError.getMessage(), response.getBody().getMessage());
    }

    @Test
    public void should_call_default_exception_handler_success() {
        var exceptionHandler = new DefaultExceptionHandler();
        var exception = new Exception("Error");

        var response = exceptionHandler.defaultErrorHandler(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DefaultExceptionHandler.REQUEST_ERROR_MESSAGE, response.getBody().getMessage());
    }
}
