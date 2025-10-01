package com.shortbreakshub.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itineraries")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String title;

    private int days;

    @Column(name = "price_from")
    private int priceFrom;

    private String hero;

    @Column(length = 1000)
    private String summary;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "itinerary_highlights",joinColumns = @JoinColumn(name = "itinerary_id"))
    private List<String> highlights = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "itinerary_schedule",joinColumns = @JoinColumn(name = "itinerary_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "day_title")),
            @AttributeOverride(name = "summary", column = @Column(name = "day_summary")),
            @AttributeOverride(name = "details", column = @Column(name = "day_details"))
    })
    @JsonProperty("schedule")
    private List<DayPlan> dayPlans = new ArrayList<>();

    public Itinerary() {}

    public Itinerary(
            String slug, String region, String country,String city, String title,
            int days, int priceFrom, String hero, String summary, List<String> highlights, List<DayPlan> dayPlans) {
        this.slug = slug;
        this.region = region;
        this.country = country;
        this.city = city;
        this.title = title;
        this.days = days;
        this.priceFrom = priceFrom;
        this.hero = hero;
        this.summary = summary;
        this.highlights = highlights;
        this.dayPlans = dayPlans;
    }

    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public String getRegion() { return region; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getTitle() { return title; }
    public int getDays() { return days; }
    public int getPriceFrom() { return priceFrom; }
    public String getHero() { return hero; }
    public String getSummary() { return summary; }
    public List<DayPlan> getDayPlans() { return dayPlans; }
    public List<String> getHighlights() { return highlights; }

    public void setId(Long id) { this.id = id; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setRegion(String region) { this.region = region; }
    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }
    public void setTitle(String title) { this.title = title; }
    public void setDays(int days) { this.days = days; }
    public void setPriceFrom(int priceFrom) { this.priceFrom = priceFrom; }
    public void setHero(String hero) { this.hero = hero; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }
    public void setDayPlans (List<DayPlan> dayPlans) { this.dayPlans = dayPlans; }

}
