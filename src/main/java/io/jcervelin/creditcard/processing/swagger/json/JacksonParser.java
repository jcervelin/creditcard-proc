package io.jcervelin.creditcard.processing.swagger.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.time.format.DateTimeFormatter.ISO_DATE;

@Configuration
public class JacksonParser {

    @Bean
    public JavaTimeModule javaTimeModule() {
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(ISO_DATE));
        return javaTimeModule;
    }

    @Bean
    @Primary
    public ObjectMapper jsonObjectMapper(final JavaTimeModule javaTimeModule) {
        return Jackson2ObjectMapperBuilder
                .json()
                .serializationInclusion(NON_NULL)
                .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
                .modules(javaTimeModule)
                .simpleDateFormat("yyyy-MM-dd")
                .build();
    }

}