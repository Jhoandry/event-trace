package com.prewave.eventTrace.services;

import com.prewave.eventTrace.repositories.models.Event;

import java.util.List;

public interface EventTraceService {
    List<Event> syncEvents();
}
