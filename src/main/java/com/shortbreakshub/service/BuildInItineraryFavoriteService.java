package com.shortbreakshub.service;

import com.shortbreakshub.model.BuildInItineraryFavorite;
import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.repository.BuildInItineraryFavoriteRepository;
import com.shortbreakshub.repository.ItineraryRepository;
import com.shortbreakshub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuildInItineraryFavoriteService {
    private final BuildInItineraryFavoriteRepository repo;
    private final UserRepository users;
    private final ItineraryRepository itineraries;

    public BuildInItineraryFavoriteService(BuildInItineraryFavoriteRepository r, UserRepository u, ItineraryRepository i) {
        this.repo = r; this.users = u; this.itineraries = i;
    }

    @Transactional
    public void addFavorite(Long userId, Long itineraryId) {
        if (repo.existsByUserIdAndItineraryId(userId, itineraryId)) return;
        var user = users.findById(userId).orElseThrow();
        var itin = itineraries.findById(itineraryId).orElseThrow();
        var f = new BuildInItineraryFavorite();
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

    public boolean isFavorite(Long userId, Long itineraryId) {
        return repo.existsByUserIdAndItineraryId(userId, itineraryId);
    }

    @Transactional(readOnly = true)
    public Page<Itinerary> getUserFavorites(Long userId, Pageable pageable) {
        return repo.findItinerariesFavoritedByUser(userId, pageable);
    }
}

