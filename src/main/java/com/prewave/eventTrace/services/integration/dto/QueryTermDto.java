package com.prewave.eventTrace.services.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryTermDto {
    private int id;
    private String text;
    private String language;
    private boolean keepOrder;
}
