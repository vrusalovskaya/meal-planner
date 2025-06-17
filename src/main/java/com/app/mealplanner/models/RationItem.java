package com.app.mealplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RationItem {
    private Long id;
    private ServiceMealType mealType;
    private Dish dish;
}
