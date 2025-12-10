package com.shortbreakshub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactReq(
        @NotBlank @Size(max = 100) String name,
        @Email @NotBlank String email,
        @NotBlank @Size(max = 2000) String message
){
}

