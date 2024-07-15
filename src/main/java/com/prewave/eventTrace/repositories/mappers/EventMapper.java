package com.prewave.eventTrace.repositories.mappers;

import com.prewave.eventTrace.repositories.models.Alert;
import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.integration.dto.ContentDto;

import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {
    public static Event fromDto(QueryTerm queryTerm, Integer alertId, Integer matches, List<ContentDto> contents) {
        return new Event(queryTerm.getId(), alertId, matches, getAlertsFromDto(contents));
    }

    private static List<Alert> getAlertsFromDto(List<ContentDto> contents) {
        return  contents.stream()
                    .map(dto -> new Alert(dto.getText(), dto.getType(), dto.getLanguage()))
                    .collect(Collectors.toList());
    }
}
