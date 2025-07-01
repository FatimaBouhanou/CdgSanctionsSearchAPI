package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matched_entities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchedEntityStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String entityId;           // Extracted from the matched ES entity

    private String matchedEntityType;  // "pwc" or "securities"

    private double score;

    private String status;             // "ok", "ko", "pending"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_id")
    @JsonBackReference
    private Alert alert;

    public MatchedEntityStatus(String entityId, String matchedEntityType, double score, String status, String name) {
        this.entityId = entityId;
        this.matchedEntityType = matchedEntityType;
        this.score = score;
        this.status = status;
        this.name = name;
    }
}
