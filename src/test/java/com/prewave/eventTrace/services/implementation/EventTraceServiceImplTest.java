package com.prewave.eventTrace.services.implementation;

import com.prewave.eventTrace.repositories.models.Content;
import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.EventService;
import com.prewave.eventTrace.services.TermsSyncService;
import com.prewave.eventTrace.services.integration.AlertService;
import com.prewave.eventTrace.services.integration.dto.AlertDto;
import com.prewave.eventTrace.services.integration.dto.ContentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventTraceServiceImplTest {

    @MockBean
    private AlertService alertService;

    @MockBean
    private TermsSyncService termsSyncService;

    @MockBean
    private EventService eventService;

    @Autowired
    private EventTraceServiceImpl eventTraceService;

    @Test
    @DisplayName("Should sync events when existing events are present")
    void testSyncEventsWithExistingEvents() throws Exception {
        // given
        List<Event> existingEvents = List.of(new Event(1, "alert1", List.of(new Content("text1", "en", 1))));
        List<AlertDto> alerts = List.of(new AlertDto("alert1", List.of(new ContentDto("term2", "text", "en")), LocalDateTime.now(), "tweet"));
        List<QueryTerm> queryTerms = List.of(new QueryTerm(2, "term2", true, "en"));

        when(eventService.fetchEvents()).thenReturn(existingEvents);
        when(alertService.fetchAlerts()).thenReturn(alerts);
        when(termsSyncService.fetchQueryTerms()).thenReturn(queryTerms);

        // when
        List<Event> result = eventTraceService.syncEvents();

        // then
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return existing events when exception occurs during sync")
    void testSyncEventsWithException() throws Exception {
        List<Event> existingEvents = List.of(new Event(1, "alert1", List.of(new Content("text1", "en", 1))));

        when(eventService.fetchEvents()).thenReturn(existingEvents);
        when(alertService.fetchAlerts()).thenThrow(new RuntimeException("Service unavailable"));

        List<Event> result = eventTraceService.syncEvents();

        assertEquals(existingEvents, result);
    }

}
