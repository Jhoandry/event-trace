package com.prewave.eventTrace.services.process;

import com.prewave.eventTrace.services.TermsSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class QueryTermScheduler {

    @Autowired
    private TermsSyncService termsSyncService;

    @Scheduled(fixedRate = 0)
    public void startupTask() {
        termsSyncService.fetchQueryTerms();
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void updateQueryTerms() {
        termsSyncService.fetchQueryTerms();
    }
}
