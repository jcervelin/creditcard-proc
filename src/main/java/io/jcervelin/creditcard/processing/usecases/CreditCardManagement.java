package io.jcervelin.creditcard.processing.usecases;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import io.jcervelin.creditcard.processing.gateways.mongo.CreditCardRepository;
import io.jcervelin.creditcard.processing.usecases.validations.CreditCardValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreditCardManagement {

    private final CreditCardRepository repository;
    private final CreditCardValidations validations;

    public void add(CreditCard creditCard) {
        validations.validate(creditCard);
        repository.save(creditCard);
    }

    public List<CreditCard> getAll() {
        return repository.findAll();
    }

}
