package com.example.demo.repository;

import com.example.demo.model.SanctionedEntity;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SanctionedEntityCustomRepositoryImpl implements SanctionedEntityCustomRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public SanctionedEntityCustomRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<SanctionedEntity> searchByName(String name) {
        Criteria criteria = new Criteria("name").contains(name);
        Query query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, SanctionedEntity.class)
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }
}

