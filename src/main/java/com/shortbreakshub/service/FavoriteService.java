package com.shortbreakshub.service;

import com.shortbreakshub.model.Favorite;
import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.repository.FavoriteRepository;
import com.shortbreakshub.repository.ItineraryRepository;
import com.shortbreakshub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {
    private final FavoriteRepository repo;
    private final UserRepository users;
    private final ItineraryRepository itineraries;

    public FavoriteService(FavoriteRepository r, UserRepository u, ItineraryRepository i) {
        this.repo = r; this.users = u; this.itineraries = i;
    }

    @Transactional
    public void addFavorite(Long userId, Long itineraryId) {
        if (repo.existsByUserIdAndItineraryId(userId, itineraryId)) return;
        var user = users.findById(userId).orElseThrow();
        var itin = itineraries.findById(itineraryId).orElseThrow();
        var f = new Favorite();
        f.setUser(user);
        f.setItinerary(itin);
        repo.save(f);
    }

    @Transactional
    public void removeFavorite(Long userId, Long itineraryId) {
        repo.deleteByUserIdAndItineraryId(userId, itineraryId);
    }

    public long countItineraryFavorites(Long itineraryId) {
        return repo.countByItineraryId(itineraryId);
    }

    public boolean isFavorited(Long userId, Long itineraryId) {
        return repo.existsByUserIdAndItineraryId(userId, itineraryId);
    }

    @Transactional(readOnly = true)
    public Page<Itinerary> getUserFavorites(Long userId, Pageable pageable) {
        return repo.findItinerariesFavoritedByUser(userId, pageable);
    }


}

