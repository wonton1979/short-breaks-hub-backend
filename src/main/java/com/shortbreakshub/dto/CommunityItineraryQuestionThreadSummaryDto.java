package com.shortbreakshub.dto;

import com.shortbreakshub.model.CommunityItineraryQuestionThread;

import java.time.Instant;

public record CommunityItineraryQuestionThreadSummaryDto(
        Long id,
        Long askerId,
        String askerUsername,
        String askerAvatarUrl,
        boolean closed,
        Instant createdAt,
        int messageCount,
        String firstMessagePreview
) {
    public static CommunityItineraryQuestionThreadSummaryDto from(CommunityItineraryQuestionThread thread) {
        var messages = thread.getMessages();
        String preview = messages.isEmpty() ? null : messages.getFirst().getContent();
        return new CommunityItineraryQuestionThreadSummaryDto(
                thread.getId(),
                thread.getAsker().getId(),
                thread.getAsker().getDisplayName(),
                thread.getAsker().getAvatarUrl(),
                thread.isClosed(),
                thread.getCreatedAt(),
                thread.getMessages().size(),
                preview
        );
    }
}

