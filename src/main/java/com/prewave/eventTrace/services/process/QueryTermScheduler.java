package com.prewave.eventTrace.services.process;

import com.prewave.eventTrace.services.TermsSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class QueryTermScheduler {

    @Autowired
    private TermsSyncService termsSyncService;

    @Scheduled(initialDelay=3000, fixedRate=900000)
    public void startupTask() {
        termsSyncService.fetchQueryTerms();
    }
}
