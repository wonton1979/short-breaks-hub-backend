package com.shortbreakshub.service;

import com.shortbreakshub.model.UserDayPlan;
import com.shortbreakshub.model.UserItinerary;
import com.shortbreakshub.respository.UserItineraryRepository;
import com.shortbreakshub.respository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserItineraryService {

    private final UserRepository userRepo;
    private final UserItineraryRepository userItineraryRepo;

    public UserItineraryService(UserRepository userRepo, UserItineraryRepository userItineraryRepo) {
        this.userRepo = userRepo;
        this.userItineraryRepo = userItineraryRepo;
    }

    @Transactional
    public void addOrUpdate(Long userId, String slug, String country,
                            String city, int days, String title,
                            String summary,String coverPhoto, String highlights,
                            String visibility, Float estimatedCost, List<UserDayPlan> dayPlans) {
        UserItinerary userItinerary = new UserItinerary();
        userItinerary.setUser(userRepo.getReferenceById(userId));
        userItinerary.setSlug(slug);
        userItinerary.setCountry(country);
        userItinerary.setCity(city);
        userItinerary.setDays(days);
        userItinerary.setTitle(title);
        userItinerary.setSummary(summary);
        userItinerary.setCoverPhoto(coverPhoto);
        userItinerary.setHighlights(highlights);
        userItinerary.setVisibility(visibility);
        userItinerary.setEstimatedCost(estimatedCost);
        userItinerary.setDayPlans(dayPlans);
        userItineraryRepo.save(userItinerary);
    }

}
