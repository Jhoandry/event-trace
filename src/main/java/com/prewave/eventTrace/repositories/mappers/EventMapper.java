package com.prewave.eventTrace.repositories.mappers;

import com.prewave.eventTrace.repositories.models.Content;
import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.services.integration.dto.ContentDto;

import java.util.List;

public class EventMapper {
    public static Event fromDto(Integer queryTermId, String alertId, List<Content> contents) {
        return new Event(queryTermId, alertId, contents);
    }

    public static Content buildContents(ContentDto contentDto, int matches) {
        return new Content(contentDto.getText(), contentDto.getLanguage(), matches);
    }
}
