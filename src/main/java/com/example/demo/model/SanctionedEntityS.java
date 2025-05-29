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
@Document(indexName = "securities")
public class SanctionedEntityS {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String caption;

    @Field(type = FieldType.Text)
    private String lei;

    @Field(type = FieldType.Text)
    private String perm_id;

    @Field(type = FieldType.Text)
    private String isins;

    @Field(type = FieldType.Text)
    private String ric;

    @Field(type = FieldType.Text)
    private String countries;

    @Field(type = FieldType.Text)
    private String sanctioned;

    @Field(type = FieldType.Text)
    private String eo14071;

    @Field(type = FieldType.Text)
    private String isPublic;

    @Field(type = FieldType.Text)
    private String url;

    @Field(type = FieldType.Text)
    private String datasets;

    @Field(type = FieldType.Text)
    private String risk_datasets;

    @Field(type = FieldType.Text)
    private String aliases;

    @Field(type = FieldType.Text)
    private String referents;
}
