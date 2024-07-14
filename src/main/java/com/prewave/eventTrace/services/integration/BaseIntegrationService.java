package com.prewave.eventTrace.services.integration;

import com.prewave.eventTrace.services.integration.webClient.WebClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
public class BaseIntegrationService {

    @Value("${prewave.api.url}")
    protected String apiUrl;

    @Value("${prewave.api.key}")
    protected String apiKey;

    @Value("${prewave.api.timeout}")
    protected Long timeout;

    @Autowired
    protected WebClientProvider provider;

    protected URI buildURIWithQueryParams(String path) {
        return provider.buildURIWithQueryParams(
                apiUrl+path,
                CollectionUtils.toMultiValueMap((Map.of("key", List.of(apiKey)))));
    }
}
