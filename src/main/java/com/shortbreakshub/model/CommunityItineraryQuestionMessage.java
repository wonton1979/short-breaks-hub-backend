package com.shortbreakshub.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community_itinerary_question_message")
public class CommunityItineraryQuestionMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Belongs to one thread
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    private CommunityItineraryQuestionThread thread;

    // Who sent this message (traveller or creator)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionMessageSender sender;

    // The actual text
    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 1000, message = "Message too long (max 1000 chars)")
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}

