package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertDTO {
    private String clientId;
    private String clientName;
    private String matchedIn; // "pwc", "securities"
    private String matchedName;
    private String matchedId;
    private double score;
    private String status;
}
