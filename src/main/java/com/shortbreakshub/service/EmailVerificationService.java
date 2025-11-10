package com.shortbreakshub.service;

import com.shortbreakshub.model.EmailVerificationToken;
import com.shortbreakshub.model.User;
import com.shortbreakshub.repository.EmailVerificationTokenRepository;
import com.shortbreakshub.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
public class EmailVerificationService {
    private final EmailVerificationTokenRepository tokenRepo;
    private final UserRepository userRepo;

    public EmailVerificationService(EmailVerificationTokenRepository tokenRepo, UserRepository userRepo) {
        this.tokenRepo = tokenRepo;
        this.userRepo = userRepo;
    }


    public EmailVerificationToken createToken(User user) {
        tokenRepo.deleteByUser(user);
        EmailVerificationToken token = new EmailVerificationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiresAt(Instant.now().plusSeconds(3600));
        return tokenRepo.save(token);
    }

    @Transactional
    public boolean verify(String rawToken) {
        var token = tokenRepo.findByToken(rawToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token"));

        if (token.getConsumedAt() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Token already used");
        }
        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Token expired");
        }

        var user = token.getUser();
        user.setEmailVerified(true);
        token.setConsumedAt(Instant.now());
        userRepo.save(user);
        tokenRepo.save(token);
        return true;
    }
}
