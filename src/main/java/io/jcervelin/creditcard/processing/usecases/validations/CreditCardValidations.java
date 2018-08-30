package io.jcervelin.creditcard.processing.usecases.validations;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreditCardValidations {

    private final List<Validator> validations;

    public void validate(final CreditCard creditCard) {
        validations.forEach(validator -> validator.validate(creditCard));
    }
}
