package com.example.demo.repository;

import com.example.demo.model.SanctionedEntityPWC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SanctionedEntityRepository extends ElasticsearchRepository<SanctionedEntityPWC, String>, SanctionedEntityCustomRepository  {



    // Enhanced search with fuzziness and multiple fields
    @Query("{\"bool\": {\"should\": [" +
            "{\"match\": {\"name\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"match\": {\"aliases\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"wildcard\": {\"name\": {\"value\": \"*?0*\"}}}," +
            "{\"wildcard\": {\"aliases\": {\"value\": \"*?0*\"}}}" +
            "]}}")
    List<SanctionedEntityPWC> searchByName(String nameQuery);

    // Paginated version of search
    @Query("{\"bool\": {\"should\": [" +
            "{\"match\": {\"name\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"match\": {\"aliases\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}" +
            "]}}")
    Page<SanctionedEntityPWC> searchByNamePaginated(String nameQuery, Pageable pageable);

    @Query("{\"match_all\":{}}") //
    List<SanctionedEntityPWC> findAllSimple();



    @Override
    Iterable<SanctionedEntityPWC> findAll();
}
    //List<SanctionedEntity> searchByName(String name);

    //List<SanctionedEntity> findByNameContainingIgnoreCase(String name);

   // List<SanctionedEntity> findByCountriesContainingIgnoreCase(String country);




