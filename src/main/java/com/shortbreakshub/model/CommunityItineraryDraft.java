package com.shortbreakshub.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Setter
@Getter
@Entity
public class CommunityItineraryDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String slug;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String region;

    @Column()
    @Min(1) @Max(7)
    private int days;

    @Column()
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column()
    private String coverPhoto;

    @Column(nullable = false,updatable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column()
    private String highlights;

    @Column()
    private Instant updatedAt;

    @Column()
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Column()
    private Float estimatedCost;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_itinerary_draft_schedule",joinColumns = @JoinColumn(name = "user_itinerary_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "details", column = @Column(name = "day_details",columnDefinition = "TEXT"))
    })
    @JsonProperty("schedule")
    private List<UserDayPlan> dayPlans = new ArrayList<>();

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    public CommunityItineraryDraft() {}

    public CommunityItineraryDraft(String slug, String country, String region,
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
