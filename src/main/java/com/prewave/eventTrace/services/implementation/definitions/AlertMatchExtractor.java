package com.prewave.eventTrace.services.implementation.definitions;

import com.prewave.eventTrace.repositories.mappers.EventMapper;
import com.prewave.eventTrace.repositories.models.Content;
import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.integration.dto.AlertDto;
import com.prewave.eventTrace.services.integration.dto.ContentDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertMatchExtractor {

    public static List<Event> extractMatches(List<AlertDto> alerts, List<QueryTerm> queryTerms, List<Event> existingEvents) {
        List<Event> eventEvents = new ArrayList<>();

        alerts.forEach(alert -> {
            filterQueryTermsByLanguage(queryTerms, alert).forEach( queryTerm -> {
                List<Content> contentMatch = getOccurrences(alert.getContents(), queryTerm);
                eventEvents.add(EventBuilder.createOrUpdate(existingEvents, eventEvents, queryTerm.getId(), alert.getId(), contentMatch));
            });
        });

        return eventEvents;
    }

    private static List<Content> getOccurrences(List<ContentDto> contents, QueryTerm queryTerm) {
        return contents.stream()
                .filter(content -> content.getLanguage().equals(queryTerm.getLanguage()))
                .map(content -> EventMapper.buildContents(content, getMatchCount(content.getText(), queryTerm)))
                .filter(content -> content.getMatches() > 0)
                .collect(Collectors.toList());
    }

    private static int getMatchCount(String text, QueryTerm queryTerm) {
        text = text.toLowerCase();
        queryTerm.setText(queryTerm.getText().toLowerCase());

        if (queryTerm.isKeepOrder()) {
            return text.contains(queryTerm.getText()) ? 1 : 0;
        } else {
            return (int) Arrays.stream(queryTerm.getText().split("\\s+"))
                    .filter(text::contains)
                    .count();
        }
    }

    private static List<QueryTerm> filterQueryTermsByLanguage(List<QueryTerm> queryTerms, AlertDto alert) {
        List<String> contentsLanguages = alert.getContents().stream().map(ContentDto::getLanguage).toList();
        return queryTerms.stream().filter(term -> contentsLanguages.contains(term.getLanguage())).collect(Collectors.toList());
    }
}
