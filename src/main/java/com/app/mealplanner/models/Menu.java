package com.app.mealplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Menu {
    private Long id;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private List<DailyRation> rations;
}
