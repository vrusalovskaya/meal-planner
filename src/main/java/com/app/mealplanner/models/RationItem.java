package com.app.mealplanner.models;

import com.app.mealplanner.common.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RationItem {
    private Long id;
    private MealType mealType;
    private Dish dish;
}
