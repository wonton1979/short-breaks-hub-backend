package com.shortbreakshub.dto;
import com.shortbreakshub.model.Currency;
import com.shortbreakshub.model.User;

public record MeResponse(
        Long userId,
        String email,
        String displayName,
        String avatarUrl,
        String location,
        String bio,
        Integer adults,
        Integer children,
        Currency currency
) {
    public static MeResponse from(User u) {
        return new MeResponse(
                u.getId(),
                u.getEmail(),
                u.getDisplayName(),
                u.getAvatarUrl(),
                u.getLocation(),
                u.getBio(),
                u.getAdults()   == null ? 1 : u.getAdults(),
                u.getChildren() == null ? 0 : u.getChildren(),
                u.getCurrency()
        );
    }
}
