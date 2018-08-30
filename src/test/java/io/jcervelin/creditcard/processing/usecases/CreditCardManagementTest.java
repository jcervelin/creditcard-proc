package io.jcervelin.creditcard.processing.usecases;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import io.jcervelin.creditcard.processing.domains.InvalidCreditCardException;
import io.jcervelin.creditcard.processing.gateways.CreditCardRepository;
import io.jcervelin.creditcard.processing.usecases.validations.CreditCardValidations;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardManagementTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private CreditCardManagement target;

    @Mock
    private CreditCardRepository repository;

    @Mock
    private CreditCardValidations validations;

    @Captor
    private ArgumentCaptor<CreditCard> creditCardArgumentCaptor;

    @Test
    public void addShouldSaveSuccessfully() {
        final CreditCard jack = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(4138207371770767L))
                .name("Jacquelyn Brown")
                .build();
        doReturn(jack).when(repository).save(jack);

        doNothing().when(validations).validate(jack);

        target.add(jack);

        verify(repository,only()).save(creditCardArgumentCaptor.capture());

        final CreditCard result = creditCardArgumentCaptor.getValue();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualToComparingFieldByField(jack);
    }

    @Test
    public void addShouldReturnInvalidCreditCardException() {
        final CreditCard juliano = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(124545643564766356L))
                .name("Juliano")
                .build();
        final InvalidCreditCardException ex = new InvalidCreditCardException("The number [124545643564766356] is not valid");
        doThrow(ex).when(validations).validate(juliano);

        thrown.expect(InvalidCreditCardException.class);
        thrown.expectMessage("The number [124545643564766356] is not valid");

        target.add(juliano);
    }

    @Test
    public void getAllShouldReturn2CreditCards() {
        final CreditCard juliano = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(1245456435646356L))
                .name("Juliano")
                .build();
        final CreditCard carol = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(1245456435646357L))
                .name("Carol")
                .build();


        doReturn(Arrays.asList(juliano,carol))
                .when(repository).findAll();

        final List<CreditCard> all = target.getAll();

        verify(repository,only()).findAll();

        Assertions.assertThat(all).isNotNull();
        Assertions.assertThat(all).containsExactlyInAnyOrder(juliano,carol);
    }
}