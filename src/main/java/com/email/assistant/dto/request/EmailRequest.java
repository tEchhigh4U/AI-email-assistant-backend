package com.email.assistant.dto.request;

import lombok.Data;

@Data
public class EmailRequest {
    private String emailContent;
    private int minWordLength;
    private int maxWordLength;
    private String tone;
    private String language;

    public EmailRequest() {
    }

    public EmailRequest(String emailContent, int minWordLength, int maxWordLength, String tone, String language) {
        this.emailContent = emailContent;
        this.minWordLength = minWordLength;
        this.maxWordLength = maxWordLength;
        this.tone = tone;
        this.language = language;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public void setMinWordLength(int minWordLength) {
        this.minWordLength = minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public void setMaxWordLength(int maxWordLength) {
        this.maxWordLength = maxWordLength;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
