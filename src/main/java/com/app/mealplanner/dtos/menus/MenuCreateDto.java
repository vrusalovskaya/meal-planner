package com.app.mealplanner.dtos.menus;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuCreateDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DailyRationCreateDto> rations;
}
