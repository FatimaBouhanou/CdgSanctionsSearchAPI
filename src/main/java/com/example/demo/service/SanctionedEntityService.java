package com.example.demo.service;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.demo.model.SanctionedEntityPWC;
import com.example.demo.model.SanctionedEntityS;
import com.example.demo.repository.SanctionedEntityCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class SanctionedEntityService {

    private final SanctionedEntityCustomRepository customRepository;
    private final ElasticsearchOperations operations;

    public SanctionedEntityService(
            @Qualifier("sanctionedEntityCustomRepositoryImpl") SanctionedEntityCustomRepository customRepository,
            ElasticsearchOperations operations) {
        this.customRepository = customRepository;
        this.operations = operations;
    }

    public Page<SanctionedEntityPWC> searchPWCByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customRepository.searchByName(name, pageable);
    }

    public Page<SanctionedEntityS> searchSByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customRepository.searchByNameS(name, pageable);
    }

    public List<SanctionedEntityPWC> getAllPWC() {
        Query matchAllQuery = MatchAllQuery.of(m -> m)._toQuery();
        NativeQuery query = NativeQuery.builder()
                .withQuery(matchAllQuery)
                .withPageable(Pageable.unpaged())
                .build();

        SearchHits<SanctionedEntityPWC> searchHits =
                operations.search(query, SanctionedEntityPWC.class);

        return searchHits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<SanctionedEntityS> getAllS() {
        Query matchAllQuery = MatchAllQuery.of(m -> m)._toQuery();
        NativeQuery query = NativeQuery.builder()
                .withQuery(matchAllQuery)
                .withPageable(Pageable.unpaged())
                .build();

        SearchHits<SanctionedEntityS> searchHits =
                operations.search(query, SanctionedEntityS.class);

        return searchHits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
