package com.shortbreakshub.model;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "community_itinerary_favorites", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","user_itinerary_id"}))
@RequiredArgsConstructor
public class CommunityItineraryFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_itinerary_id")
    @Getter @Setter
    private CommunityItinerary communityItinerary;

    @Column(nullable = false, updatable = false)
    @Getter @Setter
    private Instant createdAt = Instant.now();
}