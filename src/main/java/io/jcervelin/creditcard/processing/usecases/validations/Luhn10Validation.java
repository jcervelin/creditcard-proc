package io.jcervelin.creditcard.processing.usecases.validations;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import io.jcervelin.creditcard.processing.domains.InvalidCreditCardException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
/**
 * Algorithm Luhn used to verify if the creditcard number is valid
 */
public class Luhn10Validation implements Validator {

    private static final String LUHN10_ERROR_MESSAGE = "The number [%s] is not valid";

    public void validate(final CreditCard creditCard) {
        final String cardNumber = String.valueOf(creditCard.getCardNumber());
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        if ((sum % 10 != 0)) {
            throw new InvalidCreditCardException(String.format(LUHN10_ERROR_MESSAGE,cardNumber));
        }
    }
}