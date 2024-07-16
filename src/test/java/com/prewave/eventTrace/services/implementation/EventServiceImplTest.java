package com.prewave.eventTrace.services.implementation;

import com.prewave.eventTrace.repositories.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EventServiceImplTest {

    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private CacheManager cacheManager;

    @Value("${prewave.event.cacheName}")
    protected String eventCacheName;

    @BeforeEach
    void setUp() {
        List<Event> events = Arrays.asList(
                new Event(1, "1", List.of()),
                new Event(1, "1", List.of()));

        cacheEvents(events);
    }


    @Test
    @DisplayName("Should return events from cache")
    void shouldReturnEventsFromCache() {
        // When
        List<Event> events = eventService.fetchEvents();

        // Then
        assertEquals(2, events.size());
        assertEquals(events, events);
    }

    private void cacheEvents(List<Event> events) {
        Cache eventsCached = cacheManager.getCache(eventCacheName);
        if (eventsCached != null) {
            eventsCached.put("events", events);
        }
    }
}
