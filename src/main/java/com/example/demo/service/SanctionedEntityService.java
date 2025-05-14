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

    public List<SanctionedEntity> searchByName(String name) {
        return repository.searchByName(name);
    }
}




