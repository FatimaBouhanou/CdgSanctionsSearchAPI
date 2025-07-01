package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientId;
    private String clientName;
    private String matchedIn; // "pwc", "securities"
    private String status;
    private String birthDate;
    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MatchedEntityStatus> matchedEntities;




    // PWC matched entities
    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MatchedEntityStatus> pwcMatchedEntities;

    // Securities matched entities
    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MatchedEntityStatusSecurities> securitiesMatchedEntities;
}
