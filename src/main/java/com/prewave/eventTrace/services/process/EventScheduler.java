package com.prewave.eventTrace.services.process;

import com.prewave.eventTrace.services.EventTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EventScheduler {

    @Autowired
    private EventTraceService eventTraceService;

    @Scheduled(initialDelay=60000, fixedRate=120000)
    public void updateEvents() {
        eventTraceService.syncEvents();
    }

}
