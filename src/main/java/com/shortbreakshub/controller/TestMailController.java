package com.shortbreakshub.controller;
import com.shortbreakshub.dto.EmailReq;
import com.shortbreakshub.service.EmailService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/test-mail")
public class TestMailController {
    private final EmailService emailService;
    public TestMailController(EmailService email) { this.emailService = email; }

    @GetMapping("/html")
    public String sendHtml(@RequestBody EmailReq email){
        emailService.sendConfirmationEmail(email.to(),email.displayName(),email.confirmUrl());
        return "sent";
    }
}
