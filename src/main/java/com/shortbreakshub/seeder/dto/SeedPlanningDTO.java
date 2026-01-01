package com.shortbreakshub.seeder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeedPlanningDTO {

    private String city;

    private SeedTimeNoteDTO bestTime;
    private SeedTimeNoteDTO worstTime;

    private List<String> tips;
    private List<String> withKids;
}

