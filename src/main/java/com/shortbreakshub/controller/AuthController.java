package com.shortbreakshub.controller;

import com.shortbreakshub.dto.LoginRequest;
import com.shortbreakshub.dto.LoginResponse;
import com.shortbreakshub.dto.MeResponse;
import com.shortbreakshub.dto.UpdateMeReq;
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
        Object id = req.getAttribute("authUserId"); // Long or null
        return Map.of("userId", id);
    }

}

