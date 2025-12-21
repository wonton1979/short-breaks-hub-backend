package com.shortbreakshub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetReq(
        @Email @NotBlank String email
) {
}
