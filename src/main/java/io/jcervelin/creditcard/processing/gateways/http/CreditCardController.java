package io.jcervelin.creditcard.processing.gateways.http;

import io.jcervelin.creditcard.processing.domains.CreditCard;
import io.jcervelin.creditcard.processing.usecases.CreditCardManagement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.jcervelin.creditcard.processing.domains.Endpoints.CREDITCARD;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(CREDITCARD)
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@Api(value = CREDITCARD, description = "Operations pertaining to Creditcard processing")
public class CreditCardController {

    private final CreditCardManagement creditCardManagement;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Find all creditcards")
    public ResponseEntity<List<CreditCard>> getAll() {
        return new ResponseEntity<>(creditCardManagement.getAll(), HttpStatus.OK);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Add a creditcard")
    public ResponseEntity<List<CreditCard>> add(@RequestBody final CreditCard creditCard) {
        creditCardManagement.add(creditCard);
        return new ResponseEntity<>(creditCardManagement.getAll(), HttpStatus.OK);
    }
}
