package com.email.assistant.util;

import com.email.assistant.dto.request.EmailRequest;
import com.email.assistant.exception.custom.BadRequestException;
import com.email.assistant.exception.custom.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilderUtil {

    public static String buildPrompt(EmailRequest emailRequest) {
        // Validate input
        if (emailRequest == null || emailRequest.getEmailContent() == null || emailRequest.getEmailContent().isEmpty()) {
            throw new ResourceNotFoundException("Email Request/ Email Content Not Found.");
        } else if (emailRequest.getMinWordLength() <= 0) {
            throw new BadRequestException("Email Min Word should be greater than zero.");
        } else if (emailRequest.getMaxWordLength() <= 0) {
            throw new BadRequestException("Email Max Word should be greater than zero.");
        } else if (emailRequest.getMinWordLength() >= emailRequest.getMaxWordLength()){
            throw new BadRequestException("Email Min word should be smaller than Max Word.");
        } else if (emailRequest.getTone() == null || emailRequest.getTone().isEmpty()){
            throw new ResourceNotFoundException("Email Tone Not Found.");
        }

        // Build the prompt
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email reply for the following email content. Please don't generate a subject line.");

        // Add tone and word length constraints if provided
        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append(" Please use a ")
                    .append(emailRequest.getTone().toLowerCase())
                    .append(" tone.");
        }

        // Validate and add word length constraints
        int minWordLength = emailRequest.getMinWordLength();
        int maxWordLength = emailRequest.getMaxWordLength();
        if (minWordLength > 0 && maxWordLength > 0 && minWordLength <= maxWordLength) {
            prompt.append(" Ensure the reply is between ")
                    .append(minWordLength)
                    .append(" and ")
                    .append(maxWordLength)
                    .append(" words.");
        }

        // Validate and add language constraint
        String language = emailRequest.getLanguage();
        if (!language.isEmpty()){
            prompt.append(" Please give me the reply in ")
                    .append(language);
        }

        // Add the original email content
        prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());

        return prompt.toString();
    }
}
