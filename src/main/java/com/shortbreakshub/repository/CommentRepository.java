package com.shortbreakshub.repository;

import com.shortbreakshub.model.BuildInItineraryComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<BuildInItineraryComment, Long> {
    Page<BuildInItineraryComment> findByItineraryIdOrderByCreatedAtDesc(Long itineraryId, Pageable pageable);
    boolean existsByIdAndUserId(Long id, Long userId);
    long countByItineraryId(Long itineraryId);

    Optional<BuildInItineraryComment> findByUserIdAndItineraryId(Long userId, Long itineraryId);
}

