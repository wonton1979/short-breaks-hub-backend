package com.shortbreakshub.dto;

public record LoginRequest(String email, String password) {}
public record LoginResponse(String token, Long userId, String email, String username) {}
public record MeResponse(Long userId, String email, String username, String role) {}
