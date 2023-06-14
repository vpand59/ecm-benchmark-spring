package com.aexp.ea.ecm.controller;


import com.aexp.ea.ecm.service.FeatureHttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HttpController {

    private final FeatureHttpClient featureHttpClient;

    public HttpController(FeatureHttpClient featureHttpClient ) {
        this.featureHttpClient = featureHttpClient;
    }

    @GetMapping("/http")
    public Mono<String> HttpResponse() {
        return featureHttpClient.getValuesFromProxy();

    }
}

