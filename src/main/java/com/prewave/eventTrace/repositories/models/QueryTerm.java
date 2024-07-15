package com.prewave.eventTrace.repositories.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryTerm {
    private Integer id;
    private String text;
    private boolean keepOrder;
}
