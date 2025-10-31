package com.shortbreakshub.controller;

import com.shortbreakshub.dto.*;
import com.shortbreakshub.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public MeResponse me(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return authService.meFromToken(authorization);
    }

    @GetMapping("/debug/who")
    public Map<String,Object> who(HttpServletRequest req) {
        Object userId = req.getAttribute("authUserId"); // Long or null
        return Map.of("userId", userId);
    }

    @PostMapping("/me/renew-token")
    public ResponseEntity<String> renewToken(HttpServletRequest req) {
        Long userId = (Long) req.getAttribute("authUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = req.getAttribute("email").toString();
        String displayName = req.getAttribute("displayName").toString();
        String role = req.getAttribute("role").toString();
        return ResponseEntity.ok(authService.meRenewToken(new RenewTokenReq(userId, email, displayName, role)));
    }

}

