package com.shortbreakshub.service;

import com.shortbreakshub.dto.ContactReq;
import com.shortbreakshub.model.ContactMessage;
import com.shortbreakshub.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private final ContactMessageRepository repository;
    private final SesEmailService sesEmailService;

    public ContactService(ContactMessageRepository repository,SesEmailService sesEmailService) {
        this.repository = repository;
        this.sesEmailService = sesEmailService;
    }

    @Transactional
    public void handleContactRequest(ContactReq dto) {

        ContactMessage msg = new ContactMessage();
        msg.setName(dto.name());
        msg.setEmail(dto.email());
        msg.setMessage(dto.message());
        repository.save(msg);


        sesEmailService.sendContactEmail(
                dto.name(),
                dto.email(),
                dto.message()
        );
    }
}
