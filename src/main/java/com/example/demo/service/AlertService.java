package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.MatchedEntityStatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {

    private final SanctionedEntityService sanctionService;
    private final ClientService clientService;
    private final AlertRepository alertRepository;
    private final MatchedEntityStatusRepository matchedEntityStatusRepository;

    public AlertService(SanctionedEntityService sanctionService,
                        ClientService clientService,
                        AlertRepository alertRepository,
                        MatchedEntityStatusRepository matchedEntityStatusRepository) {
        this.sanctionService = sanctionService;
        this.clientService = clientService;
        this.alertRepository = alertRepository;
        this.matchedEntityStatusRepository = matchedEntityStatusRepository;
    }

    public List<Alert> checkAlertsForClients() {
        List<Client> clients = clientService.getAllClients();
        List<Alert> savedAlerts = new ArrayList<>();
        int pageSize = 10000;

        for (Client client : clients) {
            String name = client.getName();
            String birthDate = client.getBirthDate();
            boolean hasMatches = false;

            // PWC Matches
            var pwcMatches = sanctionService.searchPWCByNameWithScore(name, null, birthDate, 0, pageSize);
            if (!pwcMatches.getContent().isEmpty()) {
                List<MatchedEntityStatus> pwcMatchedEntities = new ArrayList<>();
                for (ScoredEntity<SanctionedEntityPWC> scoredMatch : pwcMatches.getContent()) {
                    pwcMatchedEntities.add(new MatchedEntityStatus(
                            scoredMatch.getEntity().getId(),
                            "pwc",
                            scoredMatch.getScore(),
                            "pending",
                            scoredMatch.getEntity().getName()
                    ));
                }

                Alert alert = new Alert();
                alert.setClientId(client.getId());
                alert.setClientName(client.getName());
                alert.setMatchedIn("pwc");
                alert.setStatus("ok");
                alert.setBirthDate(birthDate);
                alert.setPwcMatchedEntities(pwcMatchedEntities);

                pwcMatchedEntities.forEach(m -> m.setAlert(alert));
                savedAlerts.add(alertRepository.save(alert));
                hasMatches = true;
            }

            // Securities Matches
            var sMatches = sanctionService.searchSByNameWithScore(name, 0, pageSize);
            if (!sMatches.getContent().isEmpty()) {
                List<MatchedEntityStatusSecurities> securitiesMatchedEntities = new ArrayList<>();
                for (ScoredEntity<SanctionedEntityS> scoredMatch : sMatches.getContent()) {
                    SanctionedEntityS entity = scoredMatch.getEntity();

                    securitiesMatchedEntities.add(new MatchedEntityStatusSecurities(
                            entity.getId(),
                            entity.getCaption(),
                            entity.getLei(),
                            entity.getPerm_id(),
                            entity.getIsins(),
                            entity.getRic(),
                            entity.getCountries(),
                            entity.getSanctioned(),
                            entity.getEo14071(),
                            entity.getIsPublic(),
                            entity.getUrl(),
                            entity.getDatasets(),
                            entity.getRisk_datasets(),
                            entity.getAliases(),
                            entity.getReferents(),
                            scoredMatch.getScore(),
                            "pending"
                    ));
                }

                Alert alert = new Alert();
                alert.setClientId(client.getId());
                alert.setClientName(client.getName());
                alert.setMatchedIn("securities");
                alert.setStatus("ok");
                alert.setBirthDate(birthDate);
                alert.setSecuritiesMatchedEntities(securitiesMatchedEntities);

                securitiesMatchedEntities.forEach(m -> m.setAlert(alert));
                savedAlerts.add(alertRepository.save(alert));
                hasMatches = true;
            }

            // No matches
            if (!hasMatches) {
                Alert alert = new Alert();
                alert.setClientId(client.getId());
                alert.setClientName(client.getName());
                alert.setMatchedIn("none");
                alert.setStatus("ko");
                alert.setBirthDate(birthDate);
                alert.setPwcMatchedEntities(new ArrayList<>());
                alert.setSecuritiesMatchedEntities(new ArrayList<>());

                savedAlerts.add(alertRepository.save(alert));
            }
        }

        return savedAlerts;
    }

    // Get alerts for a specific client
    public List<Alert> getAlertsByClientId(String clientId) {
        return alertRepository.findAll().stream()
                .filter(alert -> alert.getClientId().equals(clientId))
                .toList();
    }

    // Update the status of a matched entity
    public MatchedEntityStatus updateMatchedEntityStatus(Long id, String status) {
        MatchedEntityStatus entityStatus = matchedEntityStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MatchedEntityStatus not found for id: " + id));
        entityStatus.setStatus(status);
        return matchedEntityStatusRepository.save(entityStatus);
    }

    //get all alerts
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

}
