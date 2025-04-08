package com.example.demo.controller;

import com.example.demo.model.SanctionedEntity;
import com.example.demo.model.SanctionedEntityDTO;
import com.example.demo.service.SanctionedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanctions")
public class SanctionedEntityController {

    @Autowired
    private SanctionedEntityService service;

    // Return only id and name
    @GetMapping(value = "/search", produces = "application/json")
    public List<SanctionedEntityDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String sanctionType) {

        return service.searchEntityNameOnly(name, country, sanctionType);
    }

    // Return full details of a sanctioned entity by its ID
    @GetMapping("/{id}")
    public SanctionedEntity getEntityById(@PathVariable Long id) {
        return service.getEntityById(id);
    }
}
