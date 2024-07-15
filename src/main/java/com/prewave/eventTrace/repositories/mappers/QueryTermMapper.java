package com.prewave.eventTrace.repositories.mappers;

import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.integration.dto.QueryTermDto;

public class QueryTermMapper {
    public static QueryTerm fromDto(QueryTermDto dto) {
        return new QueryTerm(dto.getId(), dto.getText(), dto.isKeepOrder());
    }
}
