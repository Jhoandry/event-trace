package com.prewave.eventTrace.services;

import com.prewave.eventTrace.repositories.models.QueryTerm;

import java.util.List;

public interface TermsSyncService {
    List<QueryTerm> fetchQueryTerms();
}
