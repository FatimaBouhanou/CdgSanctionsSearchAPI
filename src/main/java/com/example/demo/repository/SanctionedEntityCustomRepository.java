package com.example.demo.repository;

import com.example.demo.model.SanctionedEntityPWC;

import java.util.List;

public interface SanctionedEntityCustomRepository {
    List<SanctionedEntityPWC> searchByName(String name);
}
