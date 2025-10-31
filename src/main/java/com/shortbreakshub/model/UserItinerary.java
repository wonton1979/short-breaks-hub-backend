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
public class UserItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String slug;

    @NotBlank
    @Column(nullable = false)
    private String country;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Min(1) @Max(7)
    private int days;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String summary;

    @NotBlank
    @Column(nullable = false)
    private String coverPhoto;

    @Column(nullable = false,updatable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true)
    private String highlights;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private String visibility;

    @Column(nullable = true)
    private Float estimatedCost;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_itinerary_schedule",joinColumns = @JoinColumn(name = "user_itinerary_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "details", column = @Column(name = "day_details"))
    })
    @JsonProperty("schedule")
    private List<UserDayPlan> dayPlans = new ArrayList<>();

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    public UserItinerary() {}

    public UserItinerary(String slug, String country, String city,
                         int days, String title, String summary, String coverPhoto,
                         Instant createdAt, User user, String highlights, Instant updatedAt,
                         String visibility, Float estimatedCost, List<UserDayPlan> dayPlans) {
        this.slug = slug;
        this.country = country;
        this.city = city;
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
