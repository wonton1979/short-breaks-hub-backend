package com.shortbreakshub.repository;
import com.shortbreakshub.model.EmailVerificationToken;
import com.shortbreakshub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.Optional;

public interface EmailVerificationTokenRepository
        extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByToken(String token);

    void deleteAllByUser_Id(Long user_id);

    Optional<EmailVerificationToken> findTopByUser_IdOrderByExpiresAtDesc(Long userId);

    long countByUser_IdAndExpiresAtAfter(Long userId, Instant time);


}
