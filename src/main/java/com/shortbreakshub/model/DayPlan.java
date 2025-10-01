package com.shortbreakshub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DayPlan {

    @Column(name="day_number")
    private int day;

    private String title;

    @Column(length = 500)
    private String summary;

    @Column(length = 2000)
    private String details;

    public DayPlan() {}

    public DayPlan(int day, String title, String summary, String details) {
        this.day = day;
        this.title = title;
        this.summary = summary;
        this.details = details;
    }

    public int getDay() { return day; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getDetails() { return details; }
}

