package com.shortbreakshub.controller;

import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.service.ItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryService service;

    public ItineraryController(ItineraryService service) {
        this.service = service;
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Itinerary> getOne(@PathVariable String slug) {
        return service.getBySlug(slug)
                .map(ResponseEntity::ok)             // 200 + JSON body
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<Object> getfindDistinctCountryByRegion(@PathVariable String region) {
        List<String> result = service.getDistinctCountryByRegion(region);
        if (result.isEmpty()) {
            Map<String, Object> body = Map.of(
                    "status", 404,
                    "error", "No countries found for region: " + region
            );
            return ResponseEntity.status(404).body(body);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/browse/{country}")
    public ResponseEntity<Object> getByCountry(@PathVariable String country) {
        List<Itinerary> result = service.getByCountry(country.substring(0,1).toUpperCase() + country.substring(1));
        if (result.isEmpty()) {
            Map<String, Object> body = Map.of(
                    "status", 404,
                    "error", "No countries found for country: " + country
            );
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{region}")
    public ResponseEntity<Object> getByRegion(@PathVariable String region) {
        List<Itinerary> result = service.getByRegion(region);
        if (result.isEmpty()) {
            Map<String, Object> body = Map.of(
                    "status", 404,
                    "error", "No countries found for region: " + region
            );
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(result);
    }
}

