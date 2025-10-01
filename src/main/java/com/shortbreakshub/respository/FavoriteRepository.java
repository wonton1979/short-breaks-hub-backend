package com.shortbreakshub.respository;
import com.shortbreakshub.model.Favorite;
import com.shortbreakshub.model.Itinerary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserIdAndItineraryId(Long userId, Long itineraryId);
    long countByItineraryId(Long itineraryId);
    void deleteByUserIdAndItineraryId(Long userId, Long itineraryId);
    @Query("select f.itinerary from Favorite f where f.user.id = :userId order by f.createdAt desc")
    Page<Itinerary> findItinerariesFavoritedByUser(@Param("userId") Long userId, Pageable page);
}

