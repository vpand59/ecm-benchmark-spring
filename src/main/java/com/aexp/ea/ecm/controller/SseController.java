package com.aexp.ea.ecm.controller;

import com.aexp.ea.ecm.service.FeatureSseClient;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SseController {

    private final FeatureSseClient featureSseClient;

    public SseController(FeatureSseClient featureSseClient) {
        this.featureSseClient = featureSseClient;
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamSSE() {
        return featureSseClient.getStreamFromProxy()
                .map(data -> ServerSentEvent.builder(data).build())
                .onErrorResume(throwable -> {
                    // Handle errors and return a specific event if needed
                    return Mono.just(ServerSentEvent.builder("Error: " + throwable.getMessage()).build());
                });
    }

}