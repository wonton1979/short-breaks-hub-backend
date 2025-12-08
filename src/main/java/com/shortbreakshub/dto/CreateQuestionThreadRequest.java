package com.shortbreakshub.dto;

public record CreateQuestionThreadRequest(
        Long communityItineraryId,
        String content
) {
}