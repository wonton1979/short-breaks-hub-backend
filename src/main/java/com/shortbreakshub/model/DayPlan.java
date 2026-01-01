package com.shortbreakshub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DayPlan {

    @Column(name="day_number")
    private int day;

    private String title;

    private String summary;

    private String details;

    public DayPlan() {}

    public DayPlan(int day, String title, String summary, String details) {
        this.day = day;
        this.title = title;
        this.summary = summary;
        this.details = details;
    }

}

