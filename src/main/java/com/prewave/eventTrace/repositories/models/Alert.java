package com.prewave.eventTrace.repositories.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alert {
    private String text;
    private String type;
    private String language;
}
