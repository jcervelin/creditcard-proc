package io.jcervelin.creditcard.processing.gateways.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jcervelin.creditcard.processing.CreditcardProcessingApplication;
import io.jcervelin.creditcard.processing.domains.CreditCard;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;
import java.util.List;

import static io.jcervelin.creditcard.processing.domains.Endpoints.CREDITCARD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {CreditcardProcessingApplication.class})
public class CreditCardControllerIT {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mongoTemplate
                .getCollectionNames()
                .forEach(mongoTemplate::dropCollection);

        mockMvc = webAppContextSetup(webAppContext).build();
    }

    @Test
    public void getAll() throws Exception {
        // GIVEN two valid credit cards saved in the database
        final CreditCard madison = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(474131370495084L))
                .name("Madison Garcia")
                .build();
        final CreditCard jack = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(4138207371770767L))
                .name("Jacquelyn Brown")
                .build();

        mongoTemplate.save(madison);
        mongoTemplate.save(jack);

        // WHEN endpoint is called to list
        final MvcResult mvcResult = mockMvc.perform(get(CREDITCARD))
                .andExpect(status().isOk())
                .andReturn();

        // THEN all of them are listed
        final String content = mvcResult.getResponse().getContentAsString();

        final List<CreditCard> results = mapper.readValue(content, new TypeReference<List<CreditCard>>() {});

        Assertions.assertThat(results).containsExactlyInAnyOrder(madison,jack);
    }

    @Test
    public void add() throws Exception {
        // GIVEN a valid credit cards
        final CreditCard jos = CreditCard.builder()
                .cardNumber(BigInteger.valueOf(4074815635414968L))
                .name("Jos Martin")
                .build();

        //WHEN endpoint is called to save
        final MvcResult mvcResult = mockMvc.perform(post(CREDITCARD)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(jos)))
                .andExpect(status().isOk())
                .andReturn();

        // THEN return a list all creditcards with the new one included
        final String content = mvcResult.getResponse().getContentAsString();

        final List<CreditCard> results = mapper.readValue(content, new TypeReference<List<CreditCard>>() {});

        Assertions.assertThat(results).containsExactlyInAnyOrder(jos);
    }
}