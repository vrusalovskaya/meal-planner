package com.app.mealplanner.mappers;

import com.app.mealplanner.dtos.ingredients.IngredientDto;
import com.app.mealplanner.models.Ingredient;

public class ModelDtoMapper {
    public static IngredientDto toDto(Ingredient ingredient) {
        return new IngredientDto(ingredient.getId(), ingredient.getName());
    }

    public static Ingredient toModel(IngredientDto ingredientDto) {
        return new Ingredient(ingredientDto.getId(), ingredientDto.getName());
    }
}
