package com.trivia.FredySabuni.controller;

import com.trivia.FredySabuni.model.IncomingMessage;
import com.trivia.FredySabuni.repository.IncomingMessageRepository;
import com.trivia.FredySabuni.service.GameSessionService;
import com.trivia.FredySabuni.service.SMSService;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("api/africa")
public class AfricasTalkingController {

    private static final Logger log = LoggerFactory.getLogger(AfricasTalkingController.class);

    @Autowired
    private SMSService smsService;

    @Autowired
    private IncomingMessageRepository incomingMessageRepository;

    @Autowired
    private GameSessionService gameSessionService;

    public AfricasTalkingController(SMSService smsService, IncomingMessageRepository incomingMessageRepository,
                                    GameSessionService gameSessionService) {
        this.smsService = smsService;
        this.incomingMessageRepository = incomingMessageRepository;
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/to")
    public ResponseEntity<?> testSMS(@RequestParam String to, @RequestParam String msg) {
        return smsService.sendSMS(to, msg);
    }

//    @PostMapping(value = "/callback", consumes = "application/x-www-form-urlencoded")
//    public ResponseEntity<?> handleCallback(@RequestHeader("Content-Type") String contentType,
//                                            @RequestHeader("Accept") String accept,
//                                            @RequestHeader("ApiKey") String apiKey,
//                                            @RequestParam Map<String, String> formData){
//        log.info("Content-Type: " + contentType);
//        log.info("Accept: " + accept);
//        log.info("ApiKey: " + apiKey);
//        log.info("Form Data: " + formData);
//
//        return new ResponseEntity<>("Callback received", HttpStatus.OK);
//    }

    @PostMapping("/callbackAPI")
    public ResponseEntity<?> receiveSMS( @RequestBody Map<String, String> body) {

        String date = body.get("date");
        String from = body.get("from");
        String id = body.get("id");
        String linkId = body.get("linkId");
        String text = body.get("text");
        String to = body.get("to");
        String networkCode = body.get("networkCode");

        // Log the incoming message for debugging purposes
        System.out.println("Received SMS:");
        System.out.println("Date: " + date);
        System.out.println("From: " + from);
        System.out.println("ID: " + id);
        System.out.println("LinkId: " + linkId);
        System.out.println("Text: " + text);
        System.out.println("To: " + to);
        System.out.println("NetworkCode: " + networkCode);

        // Save the incoming message to the database
        IncomingMessage incomingMessage = new IncomingMessage();
        incomingMessage.setDate(date);
        incomingMessage.setFrom(from);
        incomingMessage.setMessageId(id);
        incomingMessage.setLinkId(linkId);
        incomingMessage.setText(text);
        incomingMessage.setTo(to);
        incomingMessage.setNetworkCode(networkCode);
        incomingMessage.setReceivedAt(LocalDateTime.now());

        incomingMessageRepository.save(incomingMessage);

        // Pass the user's response to the game service
        gameSessionService.handlePlayerResponse(from, text);

        return ResponseEntity.ok().build();
    }




}
