package com.shortbreakshub.service;

import com.shortbreakshub.model.PasswordResetToken;
import com.shortbreakshub.repository.PasswordResetTokenRepository;
import com.shortbreakshub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final String frontendBaseUrl;
    private final SesEmailService sesEmailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(UserRepository userRepository,
                                PasswordResetTokenRepository tokenRepository,
                                SesEmailService sesEmailService,
                                PasswordEncoder passwordEncoder,
                                @Value("${app.public-base-url}") String frontendBaseUrl) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.frontendBaseUrl = frontendBaseUrl;
        this.sesEmailService = sesEmailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createResetToken(String email) {

        String normalizedEmail = email.trim().toLowerCase();

        var userOpt = userRepository.findByEmail(normalizedEmail);

        if (userOpt.isEmpty()) {
           return;
        }

        tokenRepository.findByUser_Id(userOpt.get().getId()).ifPresent(tokenRepository::delete);

        var user = userOpt.get();

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES));
        token.setUsed(false);
        token.setConsumedAt(null);

        tokenRepository.save(token);

        String resetLink = frontendBaseUrl + "/reset-password?token=" + token.getToken();
        sesEmailService.sendPasswordResetEmail(user.getEmail(), resetLink);

    }

    @Transactional
    public void resetPassword(String tokenValue, String newPasswordRaw) {

        var token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (token.isUsed() || token.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Token expired or already used");
        }

        var user = token.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPasswordRaw));

        token.setUsed(true);
        token.setConsumedAt(Instant.now());
    }

}
