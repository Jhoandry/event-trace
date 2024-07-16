package com.prewave.eventTrace.controllers;

import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventService.fetchEvents();
    }
}
