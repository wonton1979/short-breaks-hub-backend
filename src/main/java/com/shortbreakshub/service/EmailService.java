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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private static final List<String> HERO_IMAGES = List.of(
            "https://res.cloudinary.com/wonton79/image/upload/v1765720468/australia-email_wjgfx7.jpg",
            "https://res.cloudinary.com/wonton79/image/upload/v1762635712/email-hero_gwo37i.jpg",
            "https://res.cloudinary.com/wonton79/image/upload/v1765720722/france-email_upn0cs.jpg",
            "https://res.cloudinary.com/wonton79/image/upload/v1765720818/kenya-email_tbkdma.jpg",
            "https://res.cloudinary.com/wonton79/image/upload/v1765721151/mexico-email_p27t2y.jpg",
            "https://res.cloudinary.com/wonton79/image/upload/v1765721265/canada-email_chwxli.jpg",
            "https://res.cloudinary.com/wonton79/image/upload/v1765721436/iceland-email_w8igpt.jpg"
    );


    @Value("no-reply@shortbreakhub.com")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private String pickRandomHeroImageUrl() {
        int idx = ThreadLocalRandom.current().nextInt(HERO_IMAGES.size());
        return HERO_IMAGES.get(idx);
    }

    @Async
    public void sendConfirmationEmail(String to, String userName, String confirmUrl) {
        String html = loadTemplate();
        html = html
                .replace("{{userName}}", escapeHtml(userName))
                .replace("{{confirmUrl}}", confirmUrl)
                .replace("{{heroImageUrl}}", pickRandomHeroImageUrl());
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