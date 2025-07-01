package com.example.demo.repository;

import com.example.demo.model.SanctionedEntityPWC;
import com.example.demo.model.SanctionedEntityS;
import com.example.demo.model.ScoredEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanctionedEntityCustomRepository {
    Page<SanctionedEntityPWC> searchByName(String name, String type, String birth_date, Pageable pageable);
    Page<SanctionedEntityS> searchByNameS(String name, Pageable pageable);

    // new methods to return entities + scores wrapped in a custom class
    Page<ScoredEntity<SanctionedEntityPWC>> searchByNameWithScore(String name, String type, String birth_date, Pageable pageable);
    Page<ScoredEntity<SanctionedEntityS>> searchByNameSWithScore(String name, Pageable pageable);
}
