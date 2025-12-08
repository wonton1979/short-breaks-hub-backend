package com.shortbreakshub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Embeddable
public class UserDayPlan {

    @Column(name="day_number")
    private int day;

    @Column()
    @Size(max=200)
    private String title;

    @Column(columnDefinition = "TEXT")
    @Size(min=50,max=2000)
    private String details;

    public UserDayPlan() {}

    public UserDayPlan(int day,String title, String details) {
        this.day = day;
        this.details = details;
        this.title = title;
    }
}