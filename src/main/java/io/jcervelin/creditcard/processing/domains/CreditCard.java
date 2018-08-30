package io.jcervelin.creditcard.processing.domains;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@Document(collection = "creditcards")
public class CreditCard {
    @Id
    private BigInteger cardNumber;
    private String name;
    private BigDecimal limit;
    private BigDecimal balance;
}
