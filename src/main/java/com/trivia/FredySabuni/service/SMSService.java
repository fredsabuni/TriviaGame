package com.trivia.FredySabuni.service;

import lombok.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@Service
public class SMSService {
    private final RestTemplate restTemplate;

    @Value("${sms.gateway.url}")
    private String smsGatewayUrl;

    @Value("${sms.gateway.api_key}")
    private String smsGatewayApiKey;

    public SMSService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendSMS(String phoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + smsGatewayApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();
        request.put("to", phoneNumber);
        request.put("message", message);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
        restTemplate.postForEntity(smsGatewayUrl, entity, String.class);
    }

}
