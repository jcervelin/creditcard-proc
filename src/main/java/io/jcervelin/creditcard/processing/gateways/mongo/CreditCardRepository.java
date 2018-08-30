package io.jcervelin.creditcard.processing.gateways.mongo;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditCardRepository extends MongoRepository<CreditCard,String> {
}
