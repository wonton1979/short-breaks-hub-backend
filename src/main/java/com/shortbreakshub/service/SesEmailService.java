package com.shortbreakshub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class SesEmailService {

    private final SesClient sesClient;
    private final String toEmail;
    private final String fromEmail;

    public SesEmailService(
            SesClient sesClient,
            @Value("${contact.to-email}") String toEmail,
            @Value("${contact.from-email}") String fromEmail
    ) {
        this.sesClient = sesClient;
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
    }

    public void sendContactEmail(String name, String email, String messageText) {

        String subjectText = "New contact message from " + name;

        String bodyText = """
                Name: %s
                Email: %s

                Message:
                %s
                """.formatted(name, email, messageText);

        Destination destination = Destination.builder()
                .toAddresses(toEmail)
                .build();

        Message message = Message.builder()
                .subject(Content.builder().data(subjectText).build())
                .body(Body.builder()
                        .text(Content.builder().data(bodyText).build())
                        .build())
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(destination)
                .message(message)
                .source(fromEmail)
                .build();

        sesClient.sendEmail(request);
    }

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        String subject = "Reset your Short Break Hub password";

        String bodyText = """
            We received a request to reset your Short Break Hub password.

            Click the link below to choose a new password (this link is valid for 30 minutes):

            %s

            If you didn't request a password reset, you can safely ignore this email.
            """.formatted(resetLink);

        SendEmailRequest request = SendEmailRequest.builder()
                .source(fromEmail)
                .destination(Destination.builder()
                        .toAddresses(toEmail)
                        .build())
                .message(Message.builder()
                        .subject(Content.builder()
                                .data(subject)
                                .charset("UTF-8")
                                .build())
                        .body(Body.builder()
                                .text(Content.builder()
                                        .data(bodyText)
                                        .charset("UTF-8")
                                        .build())
                                .build())
                        .build())
                .build();

        sesClient.sendEmail(request);
    }

}
