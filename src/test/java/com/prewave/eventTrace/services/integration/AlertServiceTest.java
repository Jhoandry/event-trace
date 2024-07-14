package com.prewave.eventTrace.services.integration;

import com.prewave.eventTrace.services.integration.dto.AlertDto;
import com.prewave.eventTrace.services.integration.dto.ContentDto;
import com.prewave.eventTrace.services.integration.webClient.WebClientProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AlertServiceTest {

    @Autowired
    private AlertService alertService;

    @MockBean
    private WebClientProvider provider;

    @Test
    @DisplayName("Should fetch alerts successfully")
    public void fetchAlertsSuccessfully() {
        // given
        AlertDto[] mockAlerts = {
                new AlertDto("rtct54gf34343", List.of(new ContentDto("Another disaster for Ferrari.", "text", "en")), LocalDateTime.now(), "tweet"),
                new AlertDto("hz5n855393tn", List.of(new ContentDto("MÜNCHEN (dpa-AFX) - Die IG Metall erwartet einen heißen Herbst mit vielen Auseinandersetzungen um Jobs in Deutschland.", "short", "de")), LocalDateTime.now(), "link")
        };
        when(provider.applyGET(any(), eq(AlertDto[].class), anyLong()))
                .thenReturn(mockAlerts);

        // when
        List<AlertDto> result = alertService.fetchAlerts();

        // then
        assertEquals(2, result.size());
        assertEquals("rtct54gf34343", result.getFirst().getId());
        assertEquals("Another disaster for Ferrari.", result.get(0).getContents().getFirst().getText());
        assertEquals("en", result.get(0).getContents().getFirst().getLanguage());
        assertEquals("hz5n855393tn", result.get(1).getId());
        assertEquals("MÜNCHEN (dpa-AFX) - Die IG Metall erwartet einen heißen Herbst mit vielen Auseinandersetzungen um Jobs in Deutschland.", result.get(1).getContents().getFirst().getText());
        assertEquals("de", result.get(1).getContents().getFirst().getLanguage());
        verify(provider, times(1)).applyGET(any(), eq(AlertDto[].class), anyLong());
    }

    @Test
    @DisplayName("Should handles exceptions, returning an empty list")
    public void fetchQueryTermsExceptionHandling() {
        // given
        when(provider.applyGET(any(), eq(AlertDto[].class), anyLong()))
                .thenThrow(new RuntimeException("Generic error"));

        // when
        List<AlertDto> result = alertService.fetchAlerts();

        // then
        assertEquals(0, result.size()); // Expecting empty list due to exception
    }
}
