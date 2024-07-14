package com.prewave.eventTrace.services.integration;

import com.prewave.eventTrace.services.integration.dto.QueryTermDto;
import com.prewave.eventTrace.services.integration.webClient.WebClientProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
        "prewave.api.url=https://services.prewave.fake/api/",
        "prewave.api.key=jhoandry:api-key",
        "prewave.api.timeout=300",
        "prewave.queryTerm.path=testQueryTerm",
})
public class QueryTermServiceTest {

    @Autowired
    private QueryTermService queryTermService;

    @MockBean
    private WebClientProvider provider;

    @Test
    @DisplayName("Verifies fetchQueryTerms successfully retrieves and maps query terms")
    public void fetchQueryTermsSuccessfully() {
        // given
        QueryTermDto[] mockQueryTerms = {
                new QueryTermDto(1, "term1", "en", true),
                new QueryTermDto(2, "term2", "de", false)
        };
        when(provider.applyGET(any(), eq(QueryTermDto[].class), anyLong()))
                .thenReturn(mockQueryTerms);

        // when
        List<QueryTermDto> result = queryTermService.fetchQueryTerms();

        // then
        assertEquals(2, result.size());
        assertEquals("term1", result.get(0).getText());
        assertEquals("en", result.get(0).getLanguage());
        assertEquals("term2", result.get(1).getText());
        assertEquals("de", result.get(1).getLanguage());
        verify(provider, times(1)).applyGET(any(), eq(QueryTermDto[].class), anyLong());
    }

    @Test
    @DisplayName("Ensures fetchQueryTerms handles exceptions gracefully, returning an empty list")
    public void fetchQueryTermsExceptionHandling() {
        // given
        when(provider.applyGET(any(), eq(QueryTermDto[].class), anyLong()))
                .thenThrow(new RuntimeException("Generic error"));

        // when
        List<QueryTermDto> result = queryTermService.fetchQueryTerms();

        // then
        assertEquals(0, result.size()); // Expecting empty list due to exception
    }
}
