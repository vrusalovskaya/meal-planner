package com.app.mealplanner.dtos.menus;

import com.app.mealplanner.dtos.dishes.DishDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishUpdateInMenuDto {
    private Long rationItemId;
    private Long dishId;
}
