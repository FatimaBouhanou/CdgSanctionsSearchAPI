package com.example.demo.controller;

import com.example.demo.model.SanctionedEntityPWC;
import com.example.demo.service.SanctionedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sanctions")
@CrossOrigin(origins = "*")
public class SanctionedEntityController {

    private final SanctionedEntityService service;

    public SanctionedEntityController(SanctionedEntityService service) {
        this.service = service;
    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> getAllSanctions() {
        try {
            List<SanctionedEntityPWC> sanctions = service.getAll();
            return ResponseEntity.ok(sanctions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to fetch sanctions",
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByNamePaginated(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<SanctionedEntityPWC> result = service.searchByName(name, page, size);
            return ResponseEntity.ok(Map.of(
                    "content", result.getContent(),
                    "totalPages", result.getTotalPages(),
                    "totalElements", result.getTotalElements(),
                    "page", result.getNumber()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Search failed", "message", e.getMessage()));
        }
    }

}
