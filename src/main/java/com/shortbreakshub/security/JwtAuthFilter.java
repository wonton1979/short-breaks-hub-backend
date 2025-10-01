package com.shortbreakshub.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String auth = request.getHeader("Authorization");
        Long userId = null;
        String email = null;
        String displayName = null;
        String role = null;

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                userId = jwtService.getUserId(token);
                email = jwtService.getEmail(token);
                displayName = jwtService.getDisplayName(token);
                role = jwtService.getRole(token);
                request.setAttribute("authUserId", userId);
                request.setAttribute("email", email);
                request.setAttribute("displayName", displayName);
                request.setAttribute("role", role);
            } catch (JwtException ex) {
                System.out.println("JWT invalid: " + ex.getMessage());
            }
        }

        String path = request.getRequestURI();
        if (path.startsWith("/api/secure/") && userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing or invalid token\"}");
            return;
        }

        System.out.println("JWT filter: authUserId = " + userId);
        filterChain.doFilter(request, response);
    }
}
