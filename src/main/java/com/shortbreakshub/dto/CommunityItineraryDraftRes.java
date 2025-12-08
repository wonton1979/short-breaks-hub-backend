package com.shortbreakshub.dto;

import com.shortbreakshub.model.*;

import java.time.Instant;
import java.util.List;

public record CommunityItineraryDraftRes(
        Long id,
        Long userId,
        String userDisplayName,
        String userAvatarUrl,
        String slug,
        String title,
        String country,
        String region,
        Integer days,
        String summary,
        String coverPhoto,
        String highlights,
        Visibility visibility,
        Instant createdAt,
        Instant lastUpdatedAt,
        float estimatedCost,
        List<UserDayPlan> schedule
) {
    public static CommunityItineraryDraftRes toRes(CommunityItineraryDraft draft) {
        var user = draft.getUser();
        return new CommunityItineraryDraftRes(
                draft.getId(),
                user.getId(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                draft.getSlug(),
                draft.getTitle(),
                draft.getCountry(),
                draft.getRegion(),
                draft.getDays(),
                draft.getSummary(),
                draft.getCoverPhoto(),
                draft.getHighlights(),
                draft.getVisibility(),
                draft.getCreatedAt(),
                draft.getUpdatedAt(),
                draft.getEstimatedCost(),
                draft.getDayPlans()
        );
    }
}

