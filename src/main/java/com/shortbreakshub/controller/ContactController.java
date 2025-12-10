package com.shortbreakshub.controller;

import com.shortbreakshub.dto.ContactReq;
import com.shortbreakshub.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Void> submitContact(@Valid @RequestBody ContactReq dto) {
        contactService.handleContactRequest(dto);
        return ResponseEntity.ok().build();
    }
}
