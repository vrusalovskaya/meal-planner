package com.app.mealplanner.models;

import com.app.mealplanner.common.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredient {
    private Long id;
    private Ingredient ingredient;
    private Double quantity;
    private Unit unit;
    private String comment;
}
