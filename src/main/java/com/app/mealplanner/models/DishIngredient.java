package com.app.mealplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DishIngredient {
    private Long id;
    private Ingredient ingredient;
    private Double quantity;
}
