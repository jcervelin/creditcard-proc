package io.jcervelin.creditcard.processing.domains.exceptions;

public class InvalidCreditCardException extends RuntimeException {
    public InvalidCreditCardException(String message) {
        super(message);
    }
}
