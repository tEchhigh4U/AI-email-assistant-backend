package com.email.assistant.service;

import com.email.assistant.dto.request.EmailRequest;
import com.email.assistant.util.PromptBuilderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeepSeekService {

    private final WebClient webClient;

    @Value("${DEEPSEEK_URL}")
    private String DeepSeekApiUrl;

    @Value("${DEEPSEEK_KEY}")
    private String DeepSeekApiKey;

    public DeepSeekService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<String> generateEmailReply(EmailRequest emailRequest){
        String prompt = PromptBuilderUtil.buildPrompt(emailRequest);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");

        List<Map<String,Object>> messages = new ArrayList<>();

        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful email assistant.");
        messages.add(systemMessage);

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role","user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        requestBody.put("messages", messages);

        requestBody.put("max_tokens", 1000);
        requestBody.put("temperature", 1.5);

        return webClient.post()
                .uri(DeepSeekApiUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + DeepSeekApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    // Extract response based on the API structure
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    Map<String, Object> message = (Map<String, Object>) choices.getFirst().get("message");

                    return (String) message.get("content");
                })
                .onErrorResume(e -> {
                    System.err.println("Error calling DeepSeek API: " + e.getMessage());
                    return Mono.just("Error generating response: " + e.getMessage());
                });

    }
}
