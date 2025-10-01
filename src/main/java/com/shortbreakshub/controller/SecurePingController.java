package com.shortbreakshub.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/secure")
public class SecurePingController {

    @GetMapping("/ping")
    public ResponseEntity<?> ping(HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();
        Object userId = req.getAttribute("authUserId");
        if (userId == null) {
            result.put("ok", false);
            result.put("error", "No user ID found (missing/invalid token)");
            return ResponseEntity.ok(result);
        }
        result.put("ok", true);
        result.put("userId", userId);
        return ResponseEntity.ok(result);
    }
}

