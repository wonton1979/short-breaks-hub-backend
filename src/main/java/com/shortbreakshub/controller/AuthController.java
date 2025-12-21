package com.shortbreakshub.controller;

import com.shortbreakshub.dto.*;
import com.shortbreakshub.model.User;
import com.shortbreakshub.service.AuthService;
import com.shortbreakshub.service.EmailVerificationService;
import com.shortbreakshub.service.PasswordResetService;
import com.shortbreakshub.service.UserService;
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
    private final EmailVerificationService emailVerificationService;
    private final PasswordResetService passwordResetService;
    private final UserService userService;

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

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        boolean ok = emailVerificationService.verify(token);
        if (ok) {
            return ResponseEntity.ok(Map.of("message", "Email verified successfully."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token."));
        }
    }

    @GetMapping("/verify-email-request")
    public ResponseEntity<?> verifyEmailRequest(HttpServletRequest req) {
        Long userId = (Long) req.getAttribute("authUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getUserById(userId);
        authService.resendVerificationEmail(user);
        return ResponseEntity.ok(Map.of("message", "Email verification email sent."));
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<Map<String,String>> requestPasswordReset(
            @Valid @RequestBody PasswordResetReq dto
    ) {
        passwordResetService.createResetToken(dto.email());
        return ResponseEntity.ok(Map.of("message","Password reset link has been sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody PasswordResetConfirmReq dto
    ) {
        try {
            passwordResetService.resetPassword(dto.token(), dto.newPassword());
            return ResponseEntity.ok(Map.of("message", "Password reset successfully."));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}

