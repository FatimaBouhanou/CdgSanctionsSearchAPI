package com.example.demo.repository;

import com.example.demo.model.SanctionedEntityPWC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanctionedEntityCustomRepository {
    Page<SanctionedEntityPWC> searchByName(String name, Pageable pageable);
}
