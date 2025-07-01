package com.example.demo.controller;

import com.example.demo.model.SanctionedEntityPWC;
import com.example.demo.model.SanctionedEntityS;
import com.example.demo.service.SanctionedEntityService;
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

    // PWC (Person With Control)
    @GetMapping("/list")
    public ResponseEntity<?> getAllPWC() {
        try {
            List<SanctionedEntityPWC> sanctions = service.getAllPWC();
            return ResponseEntity.ok(sanctions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch PWC sanctions", "message", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPWC(
            @RequestParam String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String birth_date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            var result = service.searchPWCByName(name, type, birth_date, page, size);
            return ResponseEntity.ok(Map.of(
                    "content", result.getContent(),
                    "totalPages", result.getTotalPages(),
                    "totalElements", result.getTotalElements(),
                    "page", result.getNumber()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Search PWC failed", "message", e.getMessage()));
        }
    }


    // S (Securities)
    @GetMapping("/securities/list")
    public ResponseEntity<?> getAllS() {
        try {
            List<SanctionedEntityS> sanctions = service.getAllS();
            return ResponseEntity.ok(sanctions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch securities sanctions", "message", e.getMessage()));
        }
    }

    @GetMapping("/securities/search")
    public ResponseEntity<?> searchS(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            var result = service.searchSByName(name, page, size);
            return ResponseEntity.ok(Map.of(
                    "content", result.getContent(),
                    "totalPages", result.getTotalPages(),
                    "totalElements", result.getTotalElements(),
                    "page", result.getNumber()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Search securities failed", "message", e.getMessage()));
        }
    }
}
