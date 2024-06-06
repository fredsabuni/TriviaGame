package com.trivia.FredySabuni.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


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

    public ResponseEntity<?> sendSMS(String phoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiKey", smsGatewayApiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", "application/json");

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("to", phoneNumber);
        request.add("message", message);
        request.add("from", "${sms.from}");
        request.add("username", "${sms.username}");

        System.out.println("Response: {} " + smsGatewayUrl);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(smsGatewayUrl, entity, String.class);
            System.out.println("Response: {} " + response.getBody());
        } catch (Exception e) {
            System.out.println("Error sending SMS" + e);
        }

        return null;
    }

}
