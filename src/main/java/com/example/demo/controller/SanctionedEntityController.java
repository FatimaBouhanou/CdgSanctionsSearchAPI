package com.example.demo.controller;

import com.example.demo.model.SanctionedEntity;
import com.example.demo.service.SanctionedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanctions")
@CrossOrigin(origins = "*") // Adjust for production
public class SanctionedEntityController {

    @Autowired
    private SanctionedEntityService service;

    @GetMapping("/search")
    public List<SanctionedEntity> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }
}
