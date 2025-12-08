package com.shortbreakshub.dto;

public record AddMessageRequest(
        Long itineraryId,
        Long threadId,
        String content
) {}
