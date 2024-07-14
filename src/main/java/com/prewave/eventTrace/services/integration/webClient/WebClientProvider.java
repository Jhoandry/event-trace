package com.prewave.eventTrace.services.integration.webClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@Component
public class WebClientProvider {

    @Autowired
    private WebClient webClient;

    public <R> R applyGET(
            URI uri,
            Class<R> responseTypeReference,
            long timeout
    ) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseTypeReference)
                .timeout(Duration.ofMillis(timeout))
                .block();
    }

    public URI buildURIWithQueryParams(String url, MultiValueMap<String, String> queryParams) {
         return UriComponentsBuilder.fromPath(url)
                 .queryParams(queryParams)
                 .build()
                 .toUri();
    }
}
