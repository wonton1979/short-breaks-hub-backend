package com.shortbreakshub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class UserDayPlan {

    @Column(name="day_number")
    private int day;

    @Column(length = 2000)
    private String details;

    public UserDayPlan() {}

    public UserDayPlan(int day, String title, String summary, String details) {
        this.day = day;
        this.details = details;
    }

}