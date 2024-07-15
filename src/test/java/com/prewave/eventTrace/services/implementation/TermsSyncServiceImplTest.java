package com.prewave.eventTrace.services.implementation;

import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.integration.QueryTermService;
import com.prewave.eventTrace.services.integration.dto.QueryTermDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TermsSyncServiceImplTest {

    @Autowired
    private TermsSyncServiceImpl termsSyncService;

    @MockBean
    private QueryTermService queryTermService;

    @Test
    @DisplayName("Should return a list of QueryTerms based on the QueryTermService response")
    void fetchQueryTermsSuccessfully() {
        // given
        List<QueryTermDto> mockQueryTermsDto = Arrays.asList(
                new QueryTermDto(1, "term1", "en", true),
                new QueryTermDto(2, "term2", "de", false)
        );
        when(queryTermService.fetchQueryTerms()).thenReturn(mockQueryTermsDto);

        // when
        List<QueryTerm> result = termsSyncService.fetchQueryTerms();

        // then
        assertEquals(2, result.size());
        assertEquals("term1", result.get(0).getText());
        assertEquals("term2", result.get(1).getText());

        // Check cache
        List<QueryTerm> cacheResult = termsSyncService.fetchQueryTerms();
        assertEquals(result, cacheResult);
    }
}
