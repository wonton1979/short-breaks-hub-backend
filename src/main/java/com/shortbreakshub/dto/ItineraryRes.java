package com.shortbreakshub.dto;

import com.shortbreakshub.model.*;

import java.util.List;

public record ItineraryRes(
        Long id,
        String slug,
        String region,
        String country,
        String city,
        String title,
        Integer days,
        int priceFrom,
        String hero,
        String summary,
        List<String> highlights,
        List<DayPlan> schedule,
        String planningCity,
        String bestTimeMonths,
        String bestTimeNote,
        String worstTimeMonths,
        String worstTimeNote,
        List<String> tips,
        List<String> withKids
) {
    public static ItineraryRes toRes(Itinerary itinerary,ItineraryPlanningSnapshot  planning) {

        return new ItineraryRes(
                itinerary.getId(),
                itinerary.getSlug(),
                itinerary.getRegion(),
                itinerary.getCountry(),
                itinerary.getCity(),
                itinerary.getTitle(),
                itinerary.getDays(),
                itinerary.getPriceFrom(),
                itinerary.getHero(),
                itinerary.getSummary(),
                itinerary.getHighlights(),
                itinerary.getDayPlans(),
                planning.getCity(),
                planning.getBestTimeMonths(),
                planning.getBestTimeNote(),
                planning.getWorstTimeMonths(),
                planning.getWorstTimeNote(),
                planning.getTips(),
                planning.getWithKids()
        );
    }
}
