package com.prewave.eventTrace.services.implementation;

import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.EventService;
import com.prewave.eventTrace.services.EventTraceService;
import com.prewave.eventTrace.services.TermsSyncService;
import com.prewave.eventTrace.services.implementation.definitions.AlertMatchExtractor;
import com.prewave.eventTrace.services.integration.AlertService;
import com.prewave.eventTrace.services.integration.dto.AlertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTraceServiceImpl implements EventTraceService {

    @Autowired
    private AlertService alertService;

    @Autowired
    private TermsSyncService termsSyncService;

    @Autowired
    private EventService eventService;

    @Override
    @Cacheable("events")
    public List<Event> syncEvents() {
        List<Event> existingEvents = eventService.fetchEvents();

        try {
            List<AlertDto> alerts = alertService.fetchAlerts();
            List<QueryTerm> queryTerms = termsSyncService.fetchQueryTerms();

            return AlertMatchExtractor.extractMatches(alerts, queryTerms, existingEvents);
        } catch (Exception e) {
            System.err.println("Error syncing Events, error message: " + e.getMessage());
            return existingEvents;
        }
    }
}
