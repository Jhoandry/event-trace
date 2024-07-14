package com.prewave.eventTrace.services.integration;


import com.prewave.eventTrace.services.integration.webClient.WebClientProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BaseIntegrationServiceTest {

    @Autowired
    private BaseIntegrationService baseIntegrationService;

    @Autowired
    private WebClientProvider provider;

    @Value("${prewave.api.url}")
    protected String apiUrl;

    @Value("${prewave.api.key}")
    protected String apiKey;

    @Test
    @DisplayName("Should Build URI With Query Params")
    public void shouldBuildUriWithQueryParams() {
        // Given
        String path = "/queryTermTest";

        // When
        URI uri = baseIntegrationService.buildURIWithQueryParams(path);

        // Then
        URI expectedUri = URI.create(apiUrl + path + "?key=" + apiKey);
        assertEquals(expectedUri, uri);
    }
}
