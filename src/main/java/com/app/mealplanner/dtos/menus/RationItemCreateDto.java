package com.app.mealplanner.dtos.menus;

import com.app.mealplanner.common.enums.MealType;
import com.app.mealplanner.dtos.dishes.DishDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RationItemCreateDto {
    private MealType mealType;
    private DishDto dish;
}
