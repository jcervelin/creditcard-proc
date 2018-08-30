package io.jcervelin.creditcard.processing.usecases.validations;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import io.jcervelin.creditcard.processing.domains.InvalidCreditCardException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

@RunWith(MockitoJUnitRunner.class)
public class Luhn10ValidationTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private Luhn10Validation target;

    @Test
    public void validateShouldExecuteWithoutException() {
        final CreditCard jack = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(4138207371770767L))
                .name("Jacquelyn Brown")
                .build();
        target.validate(jack);
    }

    @Test
    public void validateShouldExecuteWithException() {
        final CreditCard juliano = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(1111111111111111111L))
                .name("Juliano")
                .build();

        thrown.expect(InvalidCreditCardException.class);
        thrown.expectMessage(String.format("The number [%d] is not valid",juliano.getCardNumber()));

        target.validate(juliano);
    }
}