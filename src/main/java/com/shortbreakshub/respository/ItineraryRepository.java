package com.shortbreakshub.respository;

import com.shortbreakshub.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    Optional<Itinerary> findBySlug(String slug);

    @Query("select distinct i.country from Itinerary i where LOWER(i.region) = lower(:region) ")
    List<String> findDistinctCountryByRegion(@Param("region") String region);

    @Query("select i from Itinerary i where LOWER(i.region) = lower(:region) ")
    List<Itinerary> findItineraryEntitiesByRegion (@Param("region") String region);

    List<Itinerary> findByCountry(String country);
}
