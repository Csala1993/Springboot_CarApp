package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.PiesaSearchResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class PiesaSearchService {

    @Value("${serpapi.api-key}")
    private String apiKey;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://serpapi.com")
            .build();

    public List<PiesaSearchResultDTO> search(String query, String marca, String model, String codMotor) {
        if (query == null || query.isBlank()) {
            return List.of();
        }

        String enhancedQuery = String.join(" ",
                query,
                marca != null ? marca : "",
                model != null ? model : "",
                codMotor != null ? codMotor : ""
        ).trim();

        System.out.println("SERPAPI SEARCH QUERY: " + enhancedQuery);

        JsonNode response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("engine", "google_shopping")
                        .queryParam("q", enhancedQuery)
                        .queryParam("gl", "ro")
                        .queryParam("hl", "ro")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .body(JsonNode.class);

        System.out.println("SERPAPI RESPONSE:");
        System.out.println(response);

        JsonNode shoppingResults = response != null
                ? response.path("shopping_results")
                : null;

        if (shoppingResults == null || !shoppingResults.isArray()) {
            return List.of();
        }

        List<PiesaSearchResultDTO> results = new ArrayList<>();

        for (JsonNode item : shoppingResults) {
            results.add(new PiesaSearchResultDTO(
                    text(item, "title"),
                    text(item, "price"),
                    text(item, "source"),
                    firstText(item, "product_link", "link"),
                    firstText(item, "thumbnail", "serpapi_thumbnail")
            ));

            if (results.size() >= 12) {
                break;
            }
        }

        return results;
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && !value.isNull() ? value.asText() : null;
    }

    private String firstText(JsonNode node, String firstField, String secondField) {
        String firstValue = text(node, firstField);
        return firstValue != null ? firstValue : text(node, secondField);
    }
}