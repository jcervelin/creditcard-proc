package io.jcervelin.creditcard.processing.domains;

public class InvalidCreditCardException extends RuntimeException {
    public InvalidCreditCardException(String message) {
        super(message);
    }
}
