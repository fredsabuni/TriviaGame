package com.trivia.FredySabuni.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class SMSService {
    private static final Logger log = LoggerFactory.getLogger(SMSService.class);
//    private final RestTemplate restTemplate;

    @Value("${sms.gateway.url}")
    private String smsGatewayUrl;
    @Value("${sms.gateway.api_key}")
    private String smsGatewayApiKey;
    @Value("${sms.from}")
    private String smsFrom;
    @Value("${sms.username}")
    private String smsUsername;

//    public SMSService(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplate = restTemplateBuilder.build();
//    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }

    public ResponseEntity<?> sendSMS(String phoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiKey", smsGatewayApiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", "application/json");

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("to", phoneNumber);
        request.add("message", message);
        request.add("from", smsFrom);
        request.add("username", smsUsername);

        System.out.println("Response: {} " + smsGatewayUrl);
        log.info("apiKey: " +smsGatewayApiKey);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(request, headers);

        try {
            RestTemplate restTemplate = restTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(smsGatewayUrl, entity, String.class);
            System.out.println("Response: {} " + response.getBody());
        } catch (Exception e) {
            System.out.println("Error sending SMS" + e);
        }

        return null;
    }

}
