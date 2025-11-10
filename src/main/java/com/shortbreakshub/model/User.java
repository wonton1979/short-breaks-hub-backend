package com.shortbreakshub.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@RequiredArgsConstructor
public class User {
    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Email @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Getter
    @NotBlank
    @Column(nullable = false)
    private String passwordHash;

    @Setter
    @Getter
    @NotBlank
    @Column(nullable = false)
    private String displayName;

    @Getter
    @Column(nullable = false)
    private String role;

    @Setter
    @Getter
    @Column
    private String location;

    @Setter
    @Getter
    @Column(length = 500)
    private String bio;

    @Setter
    @Getter
    @Min(1)
    @Column
    private Integer adults;

    @Setter
    @Getter
    @Min(0)
    @Column
    private Integer children;

    @Setter
    @Getter
    @Column
    private String avatarUrl = null;

    @Setter
    @Getter
    @Column(name = "email_verified",nullable = false)
    private boolean emailVerified = false;

    @PrePersist
    void onCreateDefaults() {
        if (adults == null || adults < 1) adults = 1;
        if (children == null || children < 0) children = 0;
    }

    public User(String email, String passwordHash, String displayName, String location, String bio, Integer adults, Integer children) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.role = "USER";
        this.location = location;
        this.bio = bio;
        this.adults = adults;
        this.children = children;
    }

}

