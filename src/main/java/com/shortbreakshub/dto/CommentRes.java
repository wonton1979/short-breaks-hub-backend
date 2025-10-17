package com.shortbreakshub.dto;

import com.shortbreakshub.model.Comment;
import java.time.Instant;

public record CommentRes(
        Long id,
        Long userId,
        String userDisplayName,
        String userAvatarUrl,
        Long itineraryId,
        String body,
        Integer rating,
        Instant createdAt
) {
    public static CommentRes toRes(Comment c) {
        var u = c.getUser();
        return new CommentRes(
                c.getId(),
                u.getId(),
                u.getDisplayName(),
                u.getAvatarUrl(),
                c.getItinerary().getId(),
                c.getBody(),
                c.getRating(),
                c.getCreatedAt()
        );
    }
}