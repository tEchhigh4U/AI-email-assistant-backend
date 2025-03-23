package com.email.assistant.service;

import com.email.assistant.dto.request.EmailRequest;
import com.email.assistant.util.PromptBuilderUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QwenService {

    private final WebClient webClient;

    @Value("${Gwen-fast.api.url}")
    private String QwenFastApiUrl;

    @Value("${Gwen-fast.api.key}")
    private String QwenFastApiKey;

    public QwenService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String generateEmailReply(EmailRequest emailRequest) {

        // Build the prompt
        // Qwen-Fast API on the Alibaba Cloud
        String prompt = PromptBuilderUtil.buildPrompt(emailRequest);

        // Create the top-level map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","qwen-plus");

        // Create the messages list
        List<Map<String, Object>> messages = new ArrayList<>();

        // Add the system message
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful and email assistant.");
        messages.add(systemMessage);

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        // construct the request body
        requestBody.put("messages", messages);

        // Do request and get response
        String response = webClient.post()
                .uri(QwenFastApiUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + QwenFastApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Extract response and return response
        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (Exception e) {
            return "Error processing response: {e.getMessage()} ";
        }
    }

//    public String buildPrompt(EmailRequest emailRequest) {
//        // Validate input
//        if (emailRequest == null || emailRequest.getEmailContent() == null || emailRequest.getEmailContent().isEmpty()) {
//            throw new ResourceNotFoundException("Email Request/ Email Content Not Found.");
//        } else if (emailRequest.getMinWordLength() <= 0) {
//            throw new BadRequestException("Email Min Word should be greater than zero.");
//        } else if (emailRequest.getMaxWordLength() <= 0) {
//            throw new BadRequestException("Email Max Word should be greater than zero.");
//        } else if (emailRequest.getMinWordLength() >= emailRequest.getMaxWordLength()){
//            throw new BadRequestException("Email Min word should be smaller than Max Word.");
//        } else if (emailRequest.getTone() == null || emailRequest.getTone().isEmpty()){
//            throw new ResourceNotFoundException("Email Tone Not Found.");
//        }
//
//        // Build the prompt
//        StringBuilder prompt = new StringBuilder();
//        prompt.append("Generate a professional email reply for the following email content. Please don't generate a subject line.");
//
//        // Add tone and word length constraints if provided
//        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
//            prompt.append(" Please use a ")
//                    .append(emailRequest.getTone().toLowerCase())
//                    .append(" tone.");
//        }
//
//        // Validate and add word length constraints
//        int minWordLength = emailRequest.getMinWordLength();
//        int maxWordLength = emailRequest.getMaxWordLength();
//        if (minWordLength > 0 && maxWordLength > 0 && minWordLength <= maxWordLength) {
//            prompt.append(" Ensure the reply is between ")
//                    .append(minWordLength)
//                    .append(" and ")
//                    .append(maxWordLength)
//                    .append(" words.");
//        }
//
//        // Validate and add language constraint
//        String language = emailRequest.getLanguage();
//        if (!language.isEmpty()){
//            prompt.append(" Please give me the reply in ")
//                    .append(language);
//        }
//
//        // Add the original email content
//        prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
//
//        return prompt.toString();
//    }
}
