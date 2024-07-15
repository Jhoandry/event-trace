package com.prewave.eventTrace.services.implementation;

import com.prewave.eventTrace.repositories.mappers.QueryTermMapper;
import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.TermsSyncService;
import com.prewave.eventTrace.services.integration.QueryTermService;
import com.prewave.eventTrace.services.integration.dto.QueryTermDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TermsSyncServiceImpl implements TermsSyncService {

    @Autowired
    private QueryTermService queryTermService;

    @Override
    @Cacheable("queryTerms")
    public List<QueryTerm> fetchQueryTerms() {
        List<QueryTermDto> queryTermsDto = queryTermService.fetchQueryTerms();
        return queryTermsDto.stream()
                .map(QueryTermMapper::fromDto)
                .collect(Collectors.toList());
    }
}
