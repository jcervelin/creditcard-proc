package io.jcervelin.creditcard.processing.gateways.http.advice;

import io.jcervelin.creditcard.processing.domains.api.ErrorResponse;
import io.jcervelin.creditcard.processing.domains.exceptions.InvalidCreditCardException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final String ERROR_NOT_DEFINITION = "Unidentified error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        return createMessage(exception, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidCreditCardException.class)
    public ResponseEntity<ErrorResponse> noContentFound(final Exception exception) {
        return createMessage(exception, UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ErrorResponse> createMessage(final Exception exception, final HttpStatus httpStatus) {
        log.info("handleException", exception);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus);
        errorResponse.setMessage(exception.getMessage().isEmpty() ? ERROR_NOT_DEFINITION : exception.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}