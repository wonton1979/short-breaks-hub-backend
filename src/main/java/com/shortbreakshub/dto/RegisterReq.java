package com.shortbreakshub.dto;

import com.shortbreakshub.model.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterReq(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String displayName,
        String location,
        String bio,
        Integer adults,
        Integer children,
        String avatarUrl,
        Currency currency
) {}
