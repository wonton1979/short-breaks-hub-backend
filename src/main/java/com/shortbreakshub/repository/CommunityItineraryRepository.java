package com.shortbreakshub.repository;

import com.shortbreakshub.dto.UserItineraryRes;
import com.shortbreakshub.model.CommunityItinerary;
import com.shortbreakshub.model.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommunityItineraryRepository extends JpaRepository<CommunityItinerary,Long> {

    Optional<CommunityItinerary> findBySlug(String slug);

    Page<CommunityItinerary> findUserItinerariesByUser_Id(Long userId, Pageable pageable);

    @Query("select distinct i.country from CommunityItinerary i where LOWER(i.region) = lower(:region) ")
    List<String> findDistinctCountryByRegion(@Param("region") String region);

    @Query("select i from CommunityItinerary i where LOWER(i.region) = lower(:region) ")
    List<CommunityItinerary> findItinerariesByRegion (@Param("region") String region);
}
