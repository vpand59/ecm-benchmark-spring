package com.aexp.ea.ecm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class FeatureHttpClient {

    private final WebClient webClient;

    @Autowired
    public FeatureHttpClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getValuesFromProxy() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Config-Token", "db447e73-93ff-7112-bd29-2a88a8fe16c1");
        headers.set("X-Ecm-Prc-Group", "gg-ads-ecm-owner-600000712");

        String apiUrl = "https://eob-dev.aexp.com/api/v1/store/config/ecm/AIM600000712/features";
        Mono<String> response = webClient.get()
                .uri(apiUrl)
                .headers(h -> h.addAll(headers))
                .retrieve()
                .bodyToMono(String.class);

        return response;
    }

}