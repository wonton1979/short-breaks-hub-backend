package com.shortbreakshub.model;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favorites", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","itinerary_id"}))
@RequiredArgsConstructor
public class Favorite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    @Getter @Setter
    private Itinerary itinerary;

    @Column(nullable = false, updatable = false)
    @Getter @Setter
    private Instant createdAt = Instant.now();
}
