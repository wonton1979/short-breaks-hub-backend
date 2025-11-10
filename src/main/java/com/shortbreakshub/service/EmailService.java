package com.shortbreakshub.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("no-reply@shortbreakhub.com")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendConfirmationEmail(String to, String userName, String confirmUrl) {
        String html = loadTemplate();
        html = html
                .replace("{{userName}}", escapeHtml(userName))
                .replace("{{confirmUrl}}", confirmUrl);

        sendHtml(to, html);
    }


    private void sendHtml(String to, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Confirm Your Email");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Email send failed to {}: {}", to, e.getMessage(), e);
        }
    }

    private String loadTemplate() {
        try (InputStream in = new ClassPathResource("templates/email/confirm-email.html").getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load email template: " + "templates/email/confirm-email.html", e);
        }
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}