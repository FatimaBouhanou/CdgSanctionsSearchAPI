package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ClientRepository extends ElasticsearchRepository<Client, String> {
    List<Client> findByNameContainingIgnoreCase(String name);

}
