package com.example.demo.service;

import com.example.demo.model.SanctionedEntity;
import com.example.demo.model.SanctionedEntityDTO;
import com.example.demo.repository.SanctionedEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SanctionedEntityService {

    @Autowired
    private SanctionedEntityRepository repository;

    // Search and return only id + name
    public List<SanctionedEntityDTO> searchEntityNameOnly(String name, String country, String sanctionType) {
        List<SanctionedEntity> entities = searchEntities(name, country, sanctionType);
        return entities.stream()
                .map(e -> new SanctionedEntityDTO(e.getId(), e.getSanctionedName()))
                .toList();
    }

    // Search and return full entity
    public List<SanctionedEntity> searchEntities(String name, String country, String sanctionType) {
        // Start from all entities
        List<SanctionedEntity> baseList = repository.findAll();

        // Apply filters if present
        Stream<SanctionedEntity> stream = baseList.stream();

        if (name != null && !name.isEmpty()) {
            List<SanctionedEntity> byName = searchEntitiesByName(name);
            stream = stream.filter(e -> byName.contains(e));
        }

        if (country != null && !country.isEmpty()) {
            stream = stream.filter(e -> e.getSanctionCountry() != null &&
                    e.getSanctionCountry().equalsIgnoreCase(country));
        }

        if (sanctionType != null && !sanctionType.isEmpty()) {
            stream = stream.filter(e -> e.getSanctionType() != null &&
                    e.getSanctionType().equalsIgnoreCase(sanctionType));
        }

        return stream.distinct().toList();
    }


    private List<SanctionedEntity> searchEntitiesByName(String name) {
        List<SanctionedEntity> exactMatches = repository.findBySanctionedNameIgnoreCase(name);
        List<SanctionedEntity> similarMatches = repository.findByNameSimilar(name);

        return Stream.concat(exactMatches.stream(), similarMatches.stream())
                .distinct()
                .toList();
    }

    public SanctionedEntity getEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
    }
}
