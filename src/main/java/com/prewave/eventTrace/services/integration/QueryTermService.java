package com.prewave.eventTrace.services.integration;

import com.prewave.eventTrace.services.integration.dto.QueryTermDto;
import com.prewave.eventTrace.services.integration.webClient.WebClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class QueryTermService {

    @Value("${prewave.api.url}")
    private String apiUrl;

    @Value("${prewave.api.key}")
    private String apiKey;

    @Value("${prewave.api.timeout}")
    private Long timeout;

    @Value("${prewave.queryTerm.path}")
    private String queryTermPath;

    @Autowired
    private WebClientProvider provider;


    public List<QueryTermDto> fetchQueryTerms() {
        try {
            QueryTermDto[] queryTerms = provider.applyGET(
                    buildURIWithQueryParams(),
                    QueryTermDto[].class,
                    timeout);

            return List.of(queryTerms);
        } catch (Exception e) {
            System.err.println("Error getting QueryTerms, error message: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private URI buildURIWithQueryParams() {
        return provider.buildURIWithQueryParams(
                apiUrl+queryTermPath,
                CollectionUtils.toMultiValueMap((Map.of("key", List.of(apiKey)))));
    }
}

