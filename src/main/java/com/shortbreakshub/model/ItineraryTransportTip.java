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
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itinerary_transport_tip")
public class ItineraryTransportTip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "itinerary_id", nullable = false, unique = true)
    private Itinerary itinerary;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name ="arrival",columnDefinition = "jsonb", nullable = false)
    private List<Map<String,String>> arrival = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "getting_around", columnDefinition = "jsonb", nullable = false)
    private List<String> gettingAround = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name ="day_trips",columnDefinition = "jsonb", nullable = false)
    private List<Map<String,String>> dayTrips = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name ="day_moves",columnDefinition = "jsonb", nullable = false)
    private List<Map<String,String>> dayMoves = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<String> practical = new ArrayList<>();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    @PreUpdate
    void touchUpdatedAt() {
        this.updatedAt = OffsetDateTime.now();
    }
}