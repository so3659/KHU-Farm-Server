package com.laicos.khufarm.domain.delivery.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Transactional
public class DeliveryTrackerApiClientImpl implements DeliveryTrackerApiClient {

    private final RestTemplate restTemplate;
    private final String authorization;

    public DeliveryTrackerApiClientImpl(RestTemplateBuilder builder,
                                        @Value("${tracker.id}") String clientId,
                                        @Value("${tracker.secret}") String clientSecret) {
        this.restTemplate = builder.build();
        this.authorization = "TRACKQL-API-KEY " + clientId + ":" + clientSecret;
    }

    @Override
    public void registerTrackWebhook(String carrierId, String trackingNumber, String callbackUrl, String expirationTime) {
        String query = "mutation RegisterTrackWebhook($input: RegisterTrackWebhookInput!) {" +
                "  registerTrackWebhook(input: $input)" +
                "}";

        Map<String, Object> input = Map.of(
                "carrierId", carrierId,
                "trackingNumber", trackingNumber,
                "callbackUrl", callbackUrl,
                "expirationTime", expirationTime
        );

        Map<String, Object> variables = Map.of("input", input);
        Map<String, Object> body = Map.of("query", query, "variables", variables);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorization);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String apiUrl = "https://apis.tracker.delivery/graphql";
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        // 응답 처리: 정상인지 확인하거나 예외 처리 구현 가능
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Webhook registration failed: " + response.getBody());
        }
    }
}
