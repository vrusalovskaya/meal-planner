package com.app.mealplanner.dtos.menus;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DailyRationDto {
    private Long id;
    private String date;
    private List<RationItemDto> rationItems;
}
