package com.app.mealplanner.dtos.menus;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class DailyRationCreateDto {
    private LocalDate date;
    private List<RationItemCreateDto> rationItems;
}
