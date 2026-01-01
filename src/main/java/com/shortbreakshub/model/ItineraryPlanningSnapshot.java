package com.shortbreakshub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itinerary_planning_snapshot")
public class ItineraryPlanningSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "itinerary_id", nullable = false, unique = true)
    private Itinerary itinerary;

    private String city;

    @Column(name = "best_time_months", nullable = false)
    private String bestTimeMonths;

    @Column(name = "best_time_note", nullable = false)
    private String bestTimeNote;

    @Column(name = "worst_time_months", nullable = false)
    private String worstTimeMonths;

    @Column(name = "worst_time_note", nullable = false)
    private String worstTimeNote;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<String> tips = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "with_kids", columnDefinition = "jsonb", nullable = false)
    private List<String> withKids = new ArrayList<>();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    @PreUpdate
    void touchUpdatedAt() {
        this.updatedAt = OffsetDateTime.now();
    }

}
