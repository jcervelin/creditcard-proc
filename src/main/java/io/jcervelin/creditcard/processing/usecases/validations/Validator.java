package io.jcervelin.creditcard.processing.usecases.validations;

import io.jcervelin.creditcard.processing.domains.CreditCard;

public interface Validator {
    void validate(final CreditCard creditCard);
}
