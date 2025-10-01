package com.shortbreakshub.service;

import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.respository.ItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItineraryService {

    private final ItineraryRepository repo;

    public ItineraryService(ItineraryRepository repo) {
        this.repo = repo;
    }

    public List<Itinerary> getAll() {
        return repo.findAll();
    }

    public Optional<Itinerary> getBySlug(String slug) {
        return repo.findBySlug(slug);
    }

    public List<String> getDistinctCountryByRegion(String region) {
        return repo.findDistinctCountryByRegion(region);
    }

    public List<Itinerary> getByCountry(String country) {
        return repo.findByCountry(country);
    }

    public List<Itinerary> getByRegion(String region) {
        return repo.findItineraryEntitiesByRegion(region);
    }
}

