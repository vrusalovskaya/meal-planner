package com.app.mealplanner.dtos.dishes;

import com.app.mealplanner.common.enums.Unit;
import com.app.mealplanner.dtos.ingredients.IngredientDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishIngredientCreateDto {
    private IngredientDto ingredientDto;
    private Double quantity;
    private Unit unit;
    private String comment;
}
