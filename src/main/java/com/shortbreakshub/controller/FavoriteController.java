package com.shortbreakshub.controller;
import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// com.shortbreakshub.controller.FavoriteController
@RestController
@RequestMapping("/api/itineraries")
public class FavoriteController {
    private final FavoriteService favoriteService;
    public FavoriteController(FavoriteService s) { this.favoriteService = s; }

    @PostMapping("/{itineraryId}/favorite")
    public ResponseEntity<Void> favorite(@PathVariable Long itineraryId,
                                         HttpServletRequest req) {
        Long userId = (Long) req.getAttribute("authUserId");
        if (userId == null) return ResponseEntity.status(401).build();
        favoriteService.addFavorite(userId, itineraryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itineraryId}/favorite")
    public ResponseEntity<Void> unfavorite(@PathVariable Long itineraryId,
                                           HttpServletRequest req) {
        Long userId = (Long) req.getAttribute("authUserId");
        if (userId == null) return ResponseEntity.status(401).build();
        favoriteService.removeFavorite(userId, itineraryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{itineraryId}/favorites/count")
    public Map<String,Long> count(@PathVariable Long itineraryId) {
        return Map.of("count", favoriteService.countItineraryFavorites(itineraryId));
    }

    @GetMapping("/{itineraryId}/favorites/me")
    public Map<String,Boolean> mine(@PathVariable Long itineraryId,
                                    HttpServletRequest req) {
        Long userId = (Long) req.getAttribute("authUserId");
        boolean liked = (userId != null) && favoriteService.isFavorited(userId, itineraryId);
        return Map.of("liked", liked);
    }

    @GetMapping("/me/favorites")
    public ResponseEntity <Page<Itinerary>> myFavorites(
            @PageableDefault(size = 12,sort = "createdAt", direction = Sort.Direction.DESC)  Pageable pageable,
            HttpServletRequest req
    ) {
        Long userId = (Long) req.getAttribute("authUserId");
        if (userId == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(favoriteService.getUserFavorites(userId, pageable));
    }
}

