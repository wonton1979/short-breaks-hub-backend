package com.shortbreakshub.seeder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SeedTransportTipDTO {

    private List<Map<String,String>> arrival;

    private List<String> gettingAround;

    private List<Map<String,String>> dayTrips;

    private List<Map<String,String>> dayMoves;

    private List<String>  practical;

}
