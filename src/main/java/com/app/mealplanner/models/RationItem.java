package com.app.mealplanner.models;

import com.app.mealplanner.common.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RationItem {
    private Long id;
    private MealType mealType;
    private Dish dish;
}
