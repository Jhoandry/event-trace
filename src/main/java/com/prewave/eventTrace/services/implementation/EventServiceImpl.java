package com.prewave.eventTrace.services.implementation;

import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private CacheManager cacheManager;

    @Value("${prewave.event.cacheName}")
    protected String eventCacheName;

    @Override
    public List<Event> fetchEvents() {
        return getEvents();
    }

    private List<Event> getEvents() {
        try {
            Cache eventsCached = cacheManager.getCache(eventCacheName);
            return eventsCached.get("events", List.class) != null ? eventsCached.get("events", List.class) : List.of();
        } catch (Exception e) {
            System.err.println("Error getting Events, error message: " + e.getMessage());
        }

        return List.of();
    }
}