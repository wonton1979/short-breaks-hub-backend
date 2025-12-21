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
    private static final long TOKEN_TTL_SECONDS = 3600;
    private static final long COOLDOWN_SECONDS = 60;


    public EmailVerificationService(EmailVerificationTokenRepository tokenRepo, UserRepository userRepo) {
        this.tokenRepo = tokenRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public EmailVerificationToken createToken(User user) {

        var lastTokenOpt =
                tokenRepo.findTopByUser_IdOrderByExpiresAtDesc(user.getId());

        if (lastTokenOpt.isPresent()) {
            var lastToken = lastTokenOpt.get();

            if (lastToken.getExpiresAt()
                    .minusSeconds(TOKEN_TTL_SECONDS)
                    .isAfter(Instant.now().minusSeconds(COOLDOWN_SECONDS))) {

                throw new ResponseStatusException(
                        HttpStatus.TOO_MANY_REQUESTS,
                        "Please wait before requesting another verification email"
                );
            }
        }

        long sentInLast24h =
                tokenRepo.countByUser_IdAndExpiresAtAfter(
                        user.getId(),
                        Instant.now()
                );

        if (sentInLast24h >= 5) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Too many verification requests today"
            );
        }



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

        tokenRepo.deleteAllByUser_Id(user.getId());
        return true;
    }
}
