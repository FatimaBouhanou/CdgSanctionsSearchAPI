package com.example.demo.service;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.demo.model.SanctionedEntityPWC;
import com.example.demo.repository.SanctionedEntityRepository;
import io.micrometer.common.util.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.SearchHits;

@Service
public class SanctionedEntityService {

    private final SanctionedEntityRepository repository;
    private final ElasticsearchOperations operations;

    // Default constructor injection
    public SanctionedEntityService(SanctionedEntityRepository repository,
                                   ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    // Enhanced search with fuzzy matching and pagination support
    public SearchResult<SanctionedEntityPWC> searchByName(String name, int page, int size) {
        try {
            // Use match query with fuzziness or wildcards
            Query query = co.elastic.clients.elasticsearch._types.query_dsl.Query.of(q ->
                    q.bool(b -> b
                            .should(s -> s.match(m -> m
                                    .field("name")
                                    .query(name)
                                    .fuzziness("AUTO")
                            ))
                            .should(s -> s.match(m -> m
                                    .field("aliases")
                                    .query(name)
                                    .fuzziness("AUTO")
                            ))
                    )
            );


            NativeQuery nativeQuery = NativeQuery.builder()
                    .withQuery(query)
                    .withPageable(PageRequest.of(page, size))
                    .build();


            SearchHits<SanctionedEntityPWC> hits = operations.search(nativeQuery, SanctionedEntityPWC.class);

            List<SanctionedEntityPWC> content = hits.stream()
                    .map(hit -> hit.getContent())
                    .collect(Collectors.toList());

            long totalHits = hits.getTotalHits();

            return new SearchResult<>(
                    content,
                    totalHits,
                    (int) Math.ceil((double) totalHits / size),
                    page
            );
        } catch (Exception e) {
            throw new SearchException("Failed to execute search for: " + name, e);
        }
    }


    // Simple search without pagination (legacy)
    public List<SanctionedEntityPWC> searchByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return Collections.emptyList();
        }
        return repository.searchByName(name);
    }

    // Custom exceptions
    public static class SearchException extends RuntimeException {
        public SearchException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // Search result wrapper
    public static class SearchResult<T> {
        private final List<T> content;
        private final long totalElements;
        private final int totalPages;
        private final int currentPage;

        public SearchResult(List<T> content, long totalElements, int totalPages, int currentPage) {
            this.content = content;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
        }

        // Getters
        public List<T> getContent() {
            return content;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        @Override
        public String toString() {
            return "SearchResult{" +
                    "content=" + content +
                    ", totalElements=" + totalElements +
                    ", totalPages=" + totalPages +
                    ", currentPage=" + currentPage +
                    '}';
        }
    }

    public List<SanctionedEntityPWC> getAll() {
        try {
            // Use the Java client-style query (not the old QueryBuilders)
            Query matchAllQuery = MatchAllQuery.of(m -> m)._toQuery();

            NativeQuery query = NativeQuery.builder()
                    .withQuery(matchAllQuery)
                    .withPageable(Pageable.unpaged()) // ← this fetches all documents without pagination
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