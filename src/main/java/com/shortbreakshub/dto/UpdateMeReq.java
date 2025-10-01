package com.shortbreakshub.dto;

import jakarta.validation.constraints.*;

public record UpdateMeReq(
        @NotBlank @Size(min = 2, max = 50) String displayName,
        String avatarUrl,
        @Min(1)  @Max(12) Integer adults,
        @Min(0)  @Max(12) Integer children,
        String location,
        @Size(max = 500) String bio
) {
    @AssertTrue(message = "Group size cannot exceed 16")
    public boolean isTotalOk() {
        int a = adults == null ? 1 : adults;
        int c = children == null ? 0 : children;
        return (a + c) <= 16;
    }
}
