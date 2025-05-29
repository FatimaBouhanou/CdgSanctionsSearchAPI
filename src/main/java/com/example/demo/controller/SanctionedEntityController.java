package com.example.demo.controller;

import com.example.demo.model.SanctionedEntityPWC;
import com.example.demo.service.SanctionedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Add this endpoint for listing all sanctions
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

    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<?> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            SanctionedEntityService.SearchResult<SanctionedEntityPWC> result = service.searchByName(name, page, size);
            return ResponseEntity.ok(Map.of(
                    "data", result.getContent(),
                    "pagination", Map.of(
                            "totalElements", result.getTotalElements(),
                            "totalPages", result.getTotalPages(),
                            "currentPage", result.getCurrentPage()
                    )
            ));
        } catch (SanctionedEntityService.SearchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", "Invalid search request",
                            "message", e.getMessage()
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Search failed",
                            "message", e.getMessage()
                    ));
        }
    }
}