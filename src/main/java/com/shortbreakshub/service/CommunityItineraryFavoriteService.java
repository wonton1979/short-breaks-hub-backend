package com.shortbreakshub.service;

import com.shortbreakshub.model.CommunityItineraryFavorite;
import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.repository.CommunityItineraryFavoriteRepository;
import com.shortbreakshub.repository.CommunityItineraryRepository;
import com.shortbreakshub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommunityItineraryFavoriteService {
    private final CommunityItineraryFavoriteRepository repo;
    private final UserRepository users;
    private final CommunityItineraryRepository itineraries;

    public CommunityItineraryFavoriteService(CommunityItineraryFavoriteRepository r,
                                             UserRepository u,
                                             CommunityItineraryRepository i) {
        this.repo = r;
        this.users = u;
        this.itineraries = i;
    }

    @Transactional
    public void addFavorite(Long userId, Long itineraryId) {
        if (repo.existsByUserIdAndCommunityItineraryId(userId, itineraryId)) return;
        var user = users.findById(userId).orElseThrow();
        var itin = itineraries.findById(itineraryId).orElseThrow();
        var f = new CommunityItineraryFavorite();
        f.setUser(user);
        f.setCommunityItinerary(itin);
        repo.save(f);
    }

    @Transactional
    public void removeFavorite(Long userId, Long itineraryId) {
        repo.deleteByUserIdAndCommunityItineraryId(userId, itineraryId);
    }

    public long countItineraryFavorites(Long itineraryId) {
        return repo.countByCommunityItineraryId(itineraryId);
    }

    public boolean isFavorite(Long userId, Long itineraryId) {
        return repo.existsByUserIdAndCommunityItineraryId(userId, itineraryId);
    }


    @Transactional(readOnly = true)
    public Page<Itinerary> getUserFavorites(Long userId, Pageable pageable) {
        return repo.findItinerariesFavoritedByUser(userId, pageable);
    }
}