package com.prewave.eventTrace.services.implementation.definitions;

import com.prewave.eventTrace.repositories.mappers.EventMapper;
import com.prewave.eventTrace.repositories.models.Content;
import com.prewave.eventTrace.repositories.models.Event;

import java.util.List;
import java.util.Optional;

public class EventBuilder {

    public static Event createOrUpdate(List<Event> existingEvents, List<Event> currentEvents, Integer currentQueryTermId, String currentAlertId, List<Content> currentContents) {
        return fetchExistingEvent(existingEvents, currentEvents, currentQueryTermId, currentAlertId)
                .map(event -> {
                    event.setContents(getContentUpdates(event.getContents(), currentContents));
                    return event;
                }).orElse(EventMapper.fromDto(currentQueryTermId, currentAlertId, currentContents));
    }

    private static Optional<Event> fetchExistingEvent(List<Event> existingEvents, List<Event> currentEvents, Integer queryTermId, String alertId) {
        Optional<Event> currentEvent = filterEvent(currentEvents, queryTermId, alertId);
        Optional<Event> existingEvent = filterEvent(existingEvents, queryTermId, alertId);

        return currentEvent.isPresent()? currentEvent : existingEvent;
    }

    private static List<Content> getContentUpdates(List<Content> currentContents, List<Content> contents) {
        return contents.stream().map(currentContent -> {
            Optional<Content> existingContent = currentContents.stream().filter(content -> content.getText().equals(currentContent.getText())).findFirst();

            if (existingContent.isPresent()) {
                existingContent.get().setMatches(existingContent.get().getMatches() + currentContent.getMatches());
                return existingContent.get();
            } else {
                return currentContent;
            }
        }).toList();
    }

    private static Optional<Event> filterEvent(List<Event> events, Integer queryTermId, String alertId) {
        return events.stream()
                .filter(event -> event.getQueryTermId().equals(queryTermId) && event.getAlertId().equals(alertId))
                .findFirst();
    }
}
