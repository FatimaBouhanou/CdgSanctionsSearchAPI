package com.example.demo.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.demo.model.SanctionedEntityPWC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/es")
public class RawElasticSearchController {

    /*@Autowired
    private ElasticsearchClient client;

    @GetMapping("/search/name")
    public List<SanctionedEntityPWC> searchByName(
            @RequestParam String nameQuery,
            @RequestParam(defaultValue = "10") int size) throws IOException {

        SearchResponse<SanctionedEntityPWC> response = client.search(s -> s
                        .index("data") // Make sure this matches your actual index name
                        .size(size)
                        .query(q -> q
                                .multiMatch(mm -> mm
                                        .query(nameQuery)
                                        .fields("name", "aliases")
                                        .fuzziness("AUTO")
                                )
                        ),
                SanctionedEntityPWC.class // Use your entity class instead of Map
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }*/
}