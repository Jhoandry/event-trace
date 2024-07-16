package com.prewave.eventTrace.services.integration;

import com.prewave.eventTrace.services.integration.dto.AlertDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AlertService extends BaseIntegrationService {

    @Value("${prewave.alert.path}")
    private String alertPath;

    public List<AlertDto> fetchAlerts() {
        try {
            AlertDto[] alerts = provider.applyGET(
                    buildURIWithQueryParams(alertPath),
                    AlertDto[].class,
                    timeout);

            System.out.println("<<< Alerts, length: " + alerts.length );
            return List.of(alerts);
        } catch (Exception e) {
            System.err.println("Error getting Alerts, error message: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
