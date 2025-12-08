package com.shortbreakshub.dto;


import com.shortbreakshub.model.CommunityItineraryQuestionMessage;
import com.shortbreakshub.model.QuestionMessageSender;

import java.time.Instant;

public record CommunityItineraryQuestionMessageDto(
        Long id,
        QuestionMessageSender sender,
        String content,
        Instant createdAt
) {
    public static CommunityItineraryQuestionMessageDto from(CommunityItineraryQuestionMessage message) {
        return new CommunityItineraryQuestionMessageDto(
                message.getId(),
                message.getSender(),
                message.getContent(),
                message.getCreatedAt()
        );
    }
}

