package com.aexp.ea.ecm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class FeatureSseClient {

    private final WebClient webClient;

    @Autowired
    public FeatureSseClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<String> getStreamFromProxy() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Ecm-Prc-Group", "gg-ads-ecm-owner-600000712");
        headers.set("X-Config-Token", "db447e73-93ff-7112-bd29-2a88a8fe16c1");

        // Make the SSE connection to the proxy server and receive events
        String apiUrl = "https://eob-dev.aexp.com/api/v1/store/config/ecm/AIM600000712/features?sse";
        Flux<String> stream = webClient.get()
                .uri(apiUrl)
                .headers(h -> h.addAll(headers))
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .retry(3);                  //Will retry 3 times before closing connections

        return stream;

    }
}
