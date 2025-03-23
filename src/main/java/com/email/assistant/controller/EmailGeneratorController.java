package com.email.assistant.controller;

import com.email.assistant.dto.request.EmailRequest;
import com.email.assistant.service.DeepSeekService;
import com.email.assistant.service.QwenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "*")
public class EmailGeneratorController {

    private final QwenService qwenService;
    private final DeepSeekService deepSeekService;

    // Explicitly define the constructor
    public EmailGeneratorController(QwenService qwenService, DeepSeekService deepSeekService) {
        this.qwenService = qwenService;
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/qwen")
    public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest) {
        String response = qwenService.generateEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deepseek")
    public ResponseEntity<Mono<String>> generateEmailByDeepSeek(@RequestBody EmailRequest emailRequest){
        Mono<String> response = deepSeekService.generateEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    }
}
