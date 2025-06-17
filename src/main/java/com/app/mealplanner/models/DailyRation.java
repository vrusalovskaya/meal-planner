package com.app.mealplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DailyRation {
    private Long id;
    private OffsetDateTime date;
    private List<RationItem> rationItems;
}
