package com.shortbreakshub.repository;

import com.shortbreakshub.model.ItineraryPlanningSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItineraryPlanningSnapshotRepository
        extends JpaRepository<ItineraryPlanningSnapshot, Long> {

    Optional<ItineraryPlanningSnapshot> findByItinerary_Id(Long itineraryId);

    boolean existsByItinerary_Id(Long itineraryId);
}

