package com.prewave.eventTrace.services.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentDto {
    private String text;
    private String type;
    private String language;
}
