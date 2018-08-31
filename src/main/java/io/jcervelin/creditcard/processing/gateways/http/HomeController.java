package io.jcervelin.creditcard.processing.gateways.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public RedirectView home() {
        return new RedirectView("swagger-ui.html");
    }

}