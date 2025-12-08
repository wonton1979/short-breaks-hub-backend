package com.shortbreakshub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community_itinerary_question_thread")
public class CommunityItineraryQuestionThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "community_itinerary_id", nullable = false)
    private CommunityItinerary communityItinerary;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "asker_id", nullable = false)
    private User asker;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private boolean closed = false;

    @OneToMany(
            mappedBy = "thread",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("createdAt ASC")
    private List<CommunityItineraryQuestionMessage> messages = new ArrayList<>();
}

