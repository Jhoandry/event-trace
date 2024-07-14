package com.prewave.eventTrace.services.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AlertDto {
    private String id;
    private List<ContentDto> contents;
    private LocalDateTime date;
    private String inputType;
}

