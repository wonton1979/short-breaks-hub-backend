package com.shortbreakshub.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@RequiredArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank
    @Column(nullable = false)
    private String displayName;

    @Column
    private String location;

    @Column(length = 500)
    private String bio;

    @Min(1)
    @Column
    private Integer adults;

    @Min(0)
    @Column
    private Integer children;

    @Column
    private String avatarUrl = null;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Currency currency = Currency.USD;

    @Column(name = "email_verified",nullable = false)
    private boolean emailVerified = false;

    @PrePersist
    void onCreateDefaults() {
        if (adults == null || adults < 1) adults = 1;
        if (children == null || children < 0) children = 0;
    }

    public User(String email, String passwordHash, String displayName, String location,
                String bio, Integer adults, Integer children) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.location = location;
        this.bio = bio;
        this.adults = adults;
        this.children = children;
    }

}

