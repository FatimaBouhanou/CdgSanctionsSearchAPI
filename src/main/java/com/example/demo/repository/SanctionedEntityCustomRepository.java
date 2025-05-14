package com.example.demo.repository;

import com.example.demo.model.SanctionedEntity;

import java.util.List;

public interface SanctionedEntityCustomRepository {
    List<SanctionedEntity> searchByName(String name);
}
