package com.shortbreakshub.dto;

import com.shortbreakshub.model.UserDayPlan;
import com.shortbreakshub.model.Visibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import java.util.List;


public record DraftUserItineraryReq(
        String title,
        String country,
        @NotNull String region,
        @NotNull @Min(1) @Max(5) Integer days,
        float estimatedCost,
        String summary,
        String coverPhoto,
        List<UserDayPlan> userDayPlan,
        String highlights,
        @NotNull @Enumerated(EnumType.STRING) Visibility visibility
) {
}
