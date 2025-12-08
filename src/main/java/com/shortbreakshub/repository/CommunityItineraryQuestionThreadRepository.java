package com.shortbreakshub.repository;

import com.shortbreakshub.model.CommunityItineraryQuestionThread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityItineraryQuestionThreadRepository
        extends JpaRepository<CommunityItineraryQuestionThread, Long> {

    List<CommunityItineraryQuestionThread> findByCommunityItineraryId(Long itineraryId);

    List<CommunityItineraryQuestionThread> findByAskerId(Long userId);

    boolean existsByCommunityItineraryIdAndAskerId(Long aLong, Long id);
}
