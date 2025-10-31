package com.shortbreakshub.dto;

public record RenewTokenReq(Long userId,String email, String displayName, String role) {
}
