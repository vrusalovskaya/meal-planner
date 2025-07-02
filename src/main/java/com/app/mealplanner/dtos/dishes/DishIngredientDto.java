package com.app.mealplanner.dtos.dishes;

import com.app.mealplanner.common.enums.Unit;
import com.app.mealplanner.dtos.ingredients.IngredientDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishIngredientDto {
    private Long id;
    private IngredientDto ingredient;
    private Double quantity;
    private Unit unit;
    private String comment;
}
