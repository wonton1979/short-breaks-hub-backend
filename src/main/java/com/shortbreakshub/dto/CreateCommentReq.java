package com.shortbreakshub.dto;

import jakarta.validation.constraints.*;

public record CreateCommentReq(
        @NotBlank @Size(min = 2, max = 1000) String body,
        @Min(1) @Max(5) Integer rating // optional; can be null
) {}

