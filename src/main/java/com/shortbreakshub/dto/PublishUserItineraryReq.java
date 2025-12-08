package com.shortbreakshub.dto;

import com.shortbreakshub.model.Region;
import com.shortbreakshub.model.UserDayPlan;
import com.shortbreakshub.model.Visibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import java.util.List;



public record PublishUserItineraryReq(
        @NotNull @NotBlank @Size(min = 10, max = 200) String title,
        @NotNull @NotBlank String country,
        @NotNull @Enumerated(EnumType.STRING) Region region,
        @NotNull @Min(1) @Max(7) Integer days,
        float estimatedCost,
        @NotNull @NotBlank @Size(min = 50, max = 500) String summary,
        @NotNull @NotBlank String coverPhoto,
        @NotNull List<UserDayPlan> userDayPlan,
        String highlights,
        @NotNull @Enumerated(EnumType.STRING) Visibility visibility
) {
}
