package io.hfgbarrigas.oauth2.oauth2clientwebflux.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    private WebClient webClient;

    @Autowired
    public TestController(WebClient webClient) {
        this.webClient = webClient;
    }

    @RequestMapping(value = "/test", produces = {"application/json"}, method = RequestMethod.GET)
    public Mono<ResponseEntity<Object>> test() {
        return webClient
                .get()
                .uri("https://api-dev.hold.co/iam/v1/user/6")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object.class)
                .map(ResponseEntity::ok)
                .log();
    }
}
