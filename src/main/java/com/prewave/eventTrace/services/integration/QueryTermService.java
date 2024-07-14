package com.prewave.eventTrace.services.integration;

import com.prewave.eventTrace.services.integration.dto.QueryTermDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QueryTermService extends BaseIntegrationService {
    @Value("${prewave.queryTerm.path}")
    private String queryTermPath;

    public List<QueryTermDto> fetchQueryTerms() {
        try {
            QueryTermDto[] queryTerms = provider.applyGET(
                    buildURIWithQueryParams(queryTermPath),
                    QueryTermDto[].class,
                    timeout);

            return List.of(queryTerms);
        } catch (Exception e) {
            System.err.println("Error getting QueryTerms, error message: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

