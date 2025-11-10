package com.shortbreakshub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "email_verification_tokens", indexes = {
        @Index(columnList = "token", unique = true),
        @Index(columnList = "expiresAt"),
        @Index(columnList = "user_id") // helps lookups/cleanup per user
})
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // fine for MySQL/Postgres
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String token;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column
    private Instant consumedAt;

}