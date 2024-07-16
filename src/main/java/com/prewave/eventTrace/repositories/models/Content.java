package com.prewave.eventTrace.repositories.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Content {
    private String text;
    private String language;
    private Integer matches;
}
