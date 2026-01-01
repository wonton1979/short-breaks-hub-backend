package com.shortbreakshub.service;

import com.shortbreakshub.dto.ItineraryRes;
import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.model.ItineraryPlanningSnapshot;
import com.shortbreakshub.repository.ItineraryPlanningSnapshotRepository;
import com.shortbreakshub.repository.ItineraryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepo;
    private final ItineraryPlanningSnapshotRepository planningRepo;

    public ItineraryService(ItineraryRepository itineraryRepo, ItineraryPlanningSnapshotRepository planningRepo) {
        this.itineraryRepo = itineraryRepo;
        this.planningRepo = planningRepo;
    }


    public ItineraryRes getBySlug(String slug) {
        Itinerary itinerary = itineraryRepo.findBySlug(slug).orElse(null);
        if (itinerary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Itinerary Not Found");
        }
        ItineraryPlanningSnapshot planning = planningRepo.findByItinerary_Id(itinerary.getId()).orElse(null);
        if (planning == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Planning Not Found");
        }
        return ItineraryRes.toRes(itinerary,planning);
    }

    public List<String> getDistinctCountryByRegion(String region) {
        return itineraryRepo.findDistinctCountryByRegion(region);
    }

    public List<Itinerary> getByCountry(String country) {
        return itineraryRepo.findByCountry(country);
    }

    public List<Itinerary> getByRegion(String region) {
        return itineraryRepo.findItineraryEntitiesByRegion(region);
    }

    @Transactional(readOnly = true)
    public Page<Itinerary> getAllItinerariesByCustomSearch(
            String q, String country,
            Integer daysMin, Integer daysMax,
            Pageable pageable
    ) {
        return itineraryRepo.findAllItinerariesByCustomSearch(q, country, daysMin, daysMax,pageable);
    }

}

