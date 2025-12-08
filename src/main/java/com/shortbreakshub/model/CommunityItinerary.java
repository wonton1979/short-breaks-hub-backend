package com.shortbreakshub.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Setter
@Getter
@Entity
public class CommunityItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String slug;

    @NotBlank
    @Column(nullable = false)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region region;

    @Column(nullable = false)
    @Min(1) @Max(7)
    private int days;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false,columnDefinition = "TEXT")
    private String summary;

    @NotBlank
    @Column(nullable = false)
    private String coverPhoto;

    @Column(nullable = false,updatable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column()
    private String highlights;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Column()
    private Float estimatedCost;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "community_itinerary_schedule",joinColumns = @JoinColumn(name = "community_itinerary_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "details", column = @Column(name = "day_details",columnDefinition = "TEXT") )
    })
    @JsonProperty("schedule")
    private List<UserDayPlan> dayPlans = new ArrayList<>();

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    public CommunityItinerary() {}

    public CommunityItinerary(String slug, String country, Region region,
                              int days, String title, String summary, String coverPhoto,
                              Instant createdAt, User user, String highlights, Instant updatedAt,
                              Visibility visibility, Float estimatedCost, List<UserDayPlan> dayPlans) {
        this.slug = slug;
        this.country = country;
        this.region = region;
        this.days = days;
        this.title = title;
        this.summary = summary;
        this.coverPhoto = coverPhoto;
        this.createdAt = createdAt;
        this.user = user;
        this.highlights = highlights;
        this.updatedAt = updatedAt;
        this.visibility = visibility;
        this.estimatedCost = estimatedCost;
        this.dayPlans = dayPlans;
    }
}
