package io.jcervelin.creditcard.processing.usecases;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DataMongoTest
@ComponentScan(basePackages = {"io.jcervelin.creditcard.processing"})
public class CreditCardManagementIT {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CreditCardManagement target;

    @Before
    public void setup() {
        mongoTemplate
                .getCollectionNames()
                .forEach(mongoTemplate::dropCollection);
    }

    @Test
    public void add() {
        final CreditCard juliano = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(474131370495084L))
                .name("Madison Garcia")
                .build();
        final CreditCard carol = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(4138207371770767L))
                .name("Jacquelyn Brown")
                .build();

        target.add(juliano);
        target.add(carol);

        final List<CreditCard> all = mongoTemplate.findAll(CreditCard.class);
        Assertions.assertThat(all).isNotNull();
        Assertions.assertThat(all).containsExactlyInAnyOrder(juliano,carol);
    }

    @Test
    public void getAll() {
        final CreditCard juliano = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(1245456435646356L))
                .name("Juliano")
                .build();
        final CreditCard carol = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(1245456435646357L))
                .name("Carol")
                .build();

        mongoTemplate.save(juliano);
        mongoTemplate.save(carol);

        final List<CreditCard> all = target.getAll();
        Assertions.assertThat(all).isNotNull();
        Assertions.assertThat(all).containsExactlyInAnyOrder(juliano,carol);
    }
}