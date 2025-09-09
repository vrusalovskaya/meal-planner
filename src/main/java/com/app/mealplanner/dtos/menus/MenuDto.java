package com.app.mealplanner.dtos.menus;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MenuDto {
    private Long id;
    private String startDate;
    private String endDate;
    private List<DailyRationDto> rations;
}
