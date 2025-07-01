package com.example.demo.controller;

import com.example.demo.model.Alert;
import com.example.demo.model.MatchedEntityStatus;
import com.example.demo.service.AlertService;
import com.example.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;
    private final ClientService clientService;

    public AlertController(AlertService alertService, ClientService clientService) {
        this.alertService = alertService;
        this.clientService = clientService;
    }

    // Generate and save alerts for all clients
    @GetMapping("/generate")
    public ResponseEntity<?> generateAlerts() {
        try {
            List<Alert> alerts = alertService.checkAlertsForClients();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Alert check failed", "message", e.getMessage()));
        }
    }

    //  Get alerts by client ID
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAlertsByClientId(@PathVariable String clientId) {
        try {
            List<Alert> alerts = alertService.getAlertsByClientId(clientId);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Fetch failed", "message", e.getMessage()));
        }
    }

    //  Update status of a matched entity
    @PutMapping("/matched-entity/{id}/status")
    public ResponseEntity<?> updateMatchedEntityStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        try {
            MatchedEntityStatus updated = alertService.updateMatchedEntityStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Update failed", "message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAlerts() {
        try {
            List<Alert> alerts = alertService.getAllAlerts();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch alerts", "message", e.getMessage()));
        }
    }

}
