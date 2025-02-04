package com.email.assistant;

import com.email.assistant.dto.request.EmailRequest;

public class EmailRequestTest {
    public static void main(String[] args) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTone("professional");
        System.out.println(emailRequest.getTone());
    }
}
