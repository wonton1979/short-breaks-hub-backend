package com.shortbreakshub.dto;

public record LoginResponse(String token, Long userId, String email, String displayName) {}
