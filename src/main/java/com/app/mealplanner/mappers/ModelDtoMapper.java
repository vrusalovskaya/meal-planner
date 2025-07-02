package com.app.mealplanner.mappers;

import com.app.mealplanner.dtos.dishes.DishDto;
import com.app.mealplanner.dtos.dishes.DishIngredientDto;
import com.app.mealplanner.dtos.ingredients.IngredientDto;
import com.app.mealplanner.models.Dish;
import com.app.mealplanner.models.DishIngredient;
import com.app.mealplanner.models.Ingredient;
import com.app.mealplanner.models.Recipe;

import java.util.ArrayList;

public class ModelDtoMapper {
    public static IngredientDto toDto(Ingredient ingredient) {
        return new IngredientDto(ingredient.getId(), ingredient.getName(), ingredient.getPossibleUnits());
    }

    public static Ingredient toModel(IngredientDto ingredientDto) {
        return new Ingredient(ingredientDto.getId(), ingredientDto.getName(), ingredientDto.getPossibleUnits());
    }

    public static DishIngredientDto toDto(DishIngredient dishIngredient) {
        return new DishIngredientDto(dishIngredient.getId(), toDto(dishIngredient.getIngredient()), dishIngredient.getQuantity(),
                dishIngredient.getUnit(), dishIngredient.getComment());
    }

    public static DishIngredient toModel(DishIngredientDto dishIngredientDto) {
        return new DishIngredient(dishIngredientDto.getId(), toModel(dishIngredientDto.getIngredient()),
                dishIngredientDto.getQuantity(), dishIngredientDto.getUnit(), dishIngredientDto.getComment());
    }

    public static DishDto toDto(Dish dish) {
        return new DishDto(dish.getId(), dish.getName(), dish.getRecipe().getDescription(),
                dish.getRecipe().getExternalLink(), dish.getMealTypes(),
                new ArrayList<>(dish.getIngredients().stream().map(ModelDtoMapper::toDto).toList()));
    }

    public static Dish toModel(DishDto dishDto) {
        Recipe recipe = new Recipe();
        recipe.setDescription(dishDto.getRecipeDescription());
        recipe.setExternalLink(dishDto.getRecipeExternalLink());
        return new Dish(dishDto.getId(), dishDto.getName(), recipe, dishDto.getMealTypes(),
                new ArrayList<>(dishDto.getDishIngredients().stream().map(ModelDtoMapper::toModel).toList()));
    }
}
