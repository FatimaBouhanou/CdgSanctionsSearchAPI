package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "data")
public class SanctionedEntityPWC {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String schema;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String aliases;

    @Field(type = FieldType.Text)
    private String birth_date;

    @Field(type = FieldType.Text)
    private String countries;

    @Field(type = FieldType.Text)
    private String addresses;

    @Field(type = FieldType.Text)
    private String identifiers;

    @Field(type = FieldType.Text)
    private String sanctions;

    @Field(type = FieldType.Text)
    private String phones;

    @Field(type = FieldType.Text)
    private String emails;

    @Field(type = FieldType.Text)
    private String dataset;

    @Field(type = FieldType.Text)
    private String first_seen;

    @Field(type = FieldType.Text)
    private String last_seen;

    @Field(type = FieldType.Text)
    private String last_change;

    @Field(type = FieldType.Text)
    private String type;


}
