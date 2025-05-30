package com.example.demo.repository;

import com.example.demo.model.SanctionedEntityPWC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SanctionedEntityCustomRepositoryImpl implements SanctionedEntityCustomRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public SanctionedEntityCustomRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public Page<SanctionedEntityPWC> searchByName(String name, Pageable pageable) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b
                        .should(s -> s.match(m -> m.field("name.keyword").query(name).boost(5.0f)))
                        .should(s -> s.match(m -> m.field("name").query(name).fuzziness("AUTO").boost(3.0f)))
                        .should(s -> s.match(m -> m.field("aliases").query(name).fuzziness("AUTO").boost(2.0f)))
                        .should(s -> s.wildcard(w -> w.field("name").value("*" + name.toLowerCase() + "*").boost(1.5f)))
                        .should(s -> s.wildcard(w -> w.field("aliases").value("*" + name.toLowerCase() + "*").boost(1.0f)))
                        .should(s -> s.match(m -> m.field("name.phonetic").query(name).boost(2.0f)))
                ))
                .withPageable(pageable)
                .build();

        var searchHits = elasticsearchOperations.search(query, SanctionedEntityPWC.class);
        List<SanctionedEntityPWC> results = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(results, pageable, searchHits.getTotalHits());
    }
}
