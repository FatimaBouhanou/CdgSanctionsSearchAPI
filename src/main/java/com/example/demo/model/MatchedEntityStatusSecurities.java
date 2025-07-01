package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matched_entities_securities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchedEntityStatusSecurities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String caption;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String lei;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String permId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String isins;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String ric;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String countries;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String sanctioned;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String eo14071;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String isPublic;  // rename 'public' because it's a keyword in Java
    @Lob
    @Column(columnDefinition = "TEXT")
    private String entityId;  // original 'id' from securities entity
    @Lob
    @Column(columnDefinition = "TEXT")
    private String url;
    @Lob
    @Column(columnDefinition = "TEXT")

    private String datasets;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String riskDatasets;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String aliases;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String referents;

    private double score;
    private String status;  // "ok", "ko", "pending"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_id")
    @JsonBackReference
    private Alert alert;

    public MatchedEntityStatusSecurities(String entityId, String caption, String lei, String permId, String isins,
                                         String ric, String countries, String sanctioned, String eo14071, String isPublic,
                                         String url, String datasets, String riskDatasets, String aliases, String referents,
                                         double score, String status) {
        this.entityId = entityId;
        this.caption = caption;
        this.lei = lei;
        this.permId = permId;
        this.isins = isins;
        this.ric = ric;
        this.countries = countries;
        this.sanctioned = sanctioned;
        this.eo14071 = eo14071;
        this.isPublic = isPublic;
        this.url = url;
        this.datasets = datasets;
        this.riskDatasets = riskDatasets;
        this.aliases = aliases;
        this.referents = referents;
        this.score = score;
        this.status = status;
    }
}
