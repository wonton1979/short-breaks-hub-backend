package com.shortbreakshub.repository;

import com.shortbreakshub.model.CommunityItineraryQuestionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityItineraryQuestionMessageRepository
        extends JpaRepository<CommunityItineraryQuestionMessage, Long> {

    List<CommunityItineraryQuestionMessage> findByThreadIdOrderByCreatedAtAsc(Long threadId);
}
