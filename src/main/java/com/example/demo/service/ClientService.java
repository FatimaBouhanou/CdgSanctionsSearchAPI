package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ElasticsearchOperations operations;
    private final ClientRepository clientRepository;
    public ClientService(ElasticsearchOperations operations, ClientRepository clientRepository) {
        this.operations = operations;
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(m -> m))
                .withPageable(Pageable.unpaged())
                .build();

        return operations.search(query, Client.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    /*public void updateClientStatus(String clientId, String status) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found: " + clientId));
        client.setStatus(status);
        clientRepository.save(client);
    }*/
}

