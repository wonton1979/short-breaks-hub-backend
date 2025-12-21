package com.shortbreakshub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordResetConfirmReq(
        @NotBlank String token,
        @NotBlank @Size(min = 8, max = 72) String newPassword
) {
}
