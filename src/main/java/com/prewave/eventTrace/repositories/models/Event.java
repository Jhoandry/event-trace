package com.prewave.eventTrace.repositories.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Event {
    private Integer queryTermId;
    private Integer alertId;
    private Integer matches;
    private List<Alert> alertsDetail;
}
