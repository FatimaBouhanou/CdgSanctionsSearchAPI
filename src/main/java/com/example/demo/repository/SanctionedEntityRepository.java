package com.example.demo.repository;

import com.example.demo.model.SanctionedEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SanctionedEntityRepository extends ElasticsearchRepository<SanctionedEntity, String>, SanctionedEntityCustomRepository  {



    List<SanctionedEntity> searchByName(String name);

    //List<SanctionedEntity> findByNameContainingIgnoreCase(String name);

   // List<SanctionedEntity> findByCountriesContainingIgnoreCase(String country);


}

