package com.shortbreakshub.repository;
import com.shortbreakshub.model.CommunityItineraryFavorite;
import com.shortbreakshub.model.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityItineraryFavoriteRepository extends JpaRepository<CommunityItineraryFavorite, Long> {

    boolean existsByUserIdAndCommunityItineraryId(Long userId, Long communityItineraryId);

    long countByCommunityItineraryId(Long itineraryId);

    void deleteByUserIdAndCommunityItineraryId(Long userId, Long itineraryId);

    @Query("select f.communityItinerary from CommunityItineraryFavorite f where f.user.id = :userId order by f.createdAt desc")
    Page<Itinerary> findItinerariesFavoritedByUser(@Param("userId") Long userId, Pageable page);
}
