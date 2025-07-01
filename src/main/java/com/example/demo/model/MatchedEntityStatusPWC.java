package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "matched_entities_pwc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchedEntityStatusPWC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String IsSchema;
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String aliases;

    private String birthDate;


    private String countries;


    private String addresses;


    private String identifiers;


    private String sanctions;

    private String phones;


    private String emails;

    private String dataset;
    private String firstSeen;
    private String lastSeen;
    private String lastChange;

    private double score;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_id")
    @JsonBackReference
    private Alert alert;
}
