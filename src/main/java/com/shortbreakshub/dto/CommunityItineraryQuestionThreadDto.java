package com.shortbreakshub.dto;

import com.shortbreakshub.model.CommunityItineraryQuestionThread;

import java.time.Instant;
import java.util.List;

public record CommunityItineraryQuestionThreadDto(
        Long id,
        Long communityItineraryId,
        Long askerId,
        String askerUsername,
        String askerAvatarUrl,
        boolean closed,
        Instant createdAt,
        List<CommunityItineraryQuestionMessageDto> messages
) {
    public static CommunityItineraryQuestionThreadDto from(CommunityItineraryQuestionThread thread) {
        return new CommunityItineraryQuestionThreadDto(
                thread.getId(),
                thread.getCommunityItinerary().getId(),
                thread.getAsker().getId(),
                thread.getAsker().getDisplayName(),
                thread.getAsker().getAvatarUrl(),
                thread.isClosed(),
                thread.getCreatedAt(),
                thread.getMessages()
                        .stream()
                        .map(CommunityItineraryQuestionMessageDto::from)
                        .toList()
        );
    }
}
