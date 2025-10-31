package com.shortbreakshub.service;

import com.shortbreakshub.dto.LoginRequest;
import com.shortbreakshub.dto.LoginResponse;
import com.shortbreakshub.dto.MeResponse;
import com.shortbreakshub.dto.RenewTokenReq;
import com.shortbreakshub.respository.UserRepository;
import com.shortbreakshub.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository users;
    private final JwtService jwt;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    public LoginResponse login(LoginRequest req) {
        var user = users.findByEmail(req.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwt.generateToken(user.getId(), user.getEmail(), user.getDisplayName(), user.getRole());
        return new LoginResponse(token, user.getId(), user.getEmail(), user.getDisplayName());
    }

    public MeResponse meFromToken(String bearer) {
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token");
        }

        Long userId = (long) request.getAttribute("authUserId");

        var user = users.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        return MeResponse.from(user);
    }

    public String meRenewToken(RenewTokenReq req) {
        return jwt.generateToken(req.userId(),  req.email(), req.displayName(), req.role());
    }

}
