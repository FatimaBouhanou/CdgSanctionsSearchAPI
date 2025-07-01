package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
@Document(indexName = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String id;
    private String name;
    private List<String> aliases;
    private List<String> identifiers;
    private String birthDate;
    //private String status;

}
