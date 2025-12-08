package com.shortbreakshub.dto;

import com.shortbreakshub.model.Region;
import com.shortbreakshub.model.UserDayPlan;
import com.shortbreakshub.model.CommunityItinerary;
import com.shortbreakshub.model.Visibility;
import java.time.Instant;
import java.util.List;

public record UserItineraryRes(
        Long id,
        long userId,
        String userDisplayName,
        String userAvatarUrl,
        String slug,
        String title,
        String country,
        Region city,
        Integer days,
        String summary,
        String coverPhoto,
        String highlights,
        Visibility visibility,
        Instant createdAt,
        Instant lastUpdatedAt,
        List<UserDayPlan> schedule
) {
    public static UserItineraryRes toRes(CommunityItinerary communityItinerary) {
        var user = communityItinerary.getUser();
        return new UserItineraryRes(
                communityItinerary.getId(),
                user.getId(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                communityItinerary.getSlug(),
                communityItinerary.getTitle(),
                communityItinerary.getCountry(),
                communityItinerary.getRegion(),
                communityItinerary.getDays(),
                communityItinerary.getSummary(),
                communityItinerary.getCoverPhoto(),
                communityItinerary.getHighlights(),
                communityItinerary.getVisibility(),
                communityItinerary.getCreatedAt(),
                communityItinerary.getUpdatedAt(),
                communityItinerary.getDayPlans()
        );
    }
}