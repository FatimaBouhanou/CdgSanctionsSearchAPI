package com.example.demo.service;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.demo.model.SanctionedEntityPWC;
import com.example.demo.repository.SanctionedEntityRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;



@Service
public class SanctionedEntityService {

    private final SanctionedEntityRepository repository;
    private final ElasticsearchOperations operations;

    public SanctionedEntityService(SanctionedEntityRepository repository,
                                   ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    public Page<SanctionedEntityPWC> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.searchByName(name, pageable);
    }


    public List<SanctionedEntityPWC> getAll() {
        try {
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

        } catch (Exception e) {
            throw new RuntimeException("Error fetching all sanctions: " + e.getMessage(), e);
        }
    }
}
