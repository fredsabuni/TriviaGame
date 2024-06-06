package com.trivia.FredySabuni.controller;

import com.trivia.FredySabuni.service.SMSService;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/africa")
public class AfricasTalkingController {

    private static final Logger log = LoggerFactory.getLogger(AfricasTalkingController.class);
    @Autowired
    private SMSService smsService;

    @PostMapping("/to")
    public ResponseEntity<?> testSMS(@RequestParam String to, @RequestParam String msg) {
        return smsService.sendSMS(to, msg);
    }

    @PostMapping(value = "/callback", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> handleCallback(@RequestHeader("Content-Type") String contentType,
                                            @RequestHeader("Accept") String accept,
                                            @RequestHeader("ApiKey") String apiKey,
                                            @RequestParam Map<String, String> formData){
        log.info("Content-Type: " + contentType);
        log.info("Accept: " + accept);
        log.info("ApiKey: " + apiKey);
        log.info("Form Data: " + formData);

        return new ResponseEntity<>("Callback received", HttpStatus.OK);
    }


}
