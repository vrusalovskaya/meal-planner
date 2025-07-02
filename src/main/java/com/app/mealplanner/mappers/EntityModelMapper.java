package com.app.mealplanner.mappers;

import com.app.mealplanner.entities.*;
import com.app.mealplanner.models.*;

import java.util.ArrayList;

public class EntityModelMapper {
    public static Ingredient toModel(IngredientEntity ingredientEntity) {
        return new Ingredient(ingredientEntity.getId(), ingredientEntity.getName(), ingredientEntity.getPossibleUnits());
    }

    public static IngredientEntity toEntity(Ingredient ingredient) {
        return new IngredientEntity(ingredient.getId(), ingredient.getName(), ingredient.getPossibleUnits());
    }

    public static Recipe toModel(RecipeEntity recipeEntity) {
        return new Recipe(recipeEntity.getId(), recipeEntity.getDescription(), recipeEntity.getExternalLink());
    }

    public static RecipeEntity toEntity(Recipe recipe) {
        return new RecipeEntity(recipe.getId(), recipe.getDescription(), recipe.getExternalLink());
    }

    public static DishIngredient toModel(DishIngredientEntity dishIngredientEntity) {
        return new DishIngredient(dishIngredientEntity.getId(), toModel(dishIngredientEntity.getIngredient()),
                dishIngredientEntity.getQuantity(), dishIngredientEntity.getUnit(), dishIngredientEntity.getComment());
    }

    public static DishIngredientEntity toEntity(DishIngredient dishIngredient) {
        return new DishIngredientEntity(dishIngredient.getId(), toEntity(dishIngredient.getIngredient()),
                dishIngredient.getQuantity(), dishIngredient.getUnit(), dishIngredient.getComment());
    }

    public static RationItem toModel(RationItemEntity rationItemEntity) {
        return new RationItem(rationItemEntity.getId(), rationItemEntity.getMealType(),
                toModel(rationItemEntity.getDish()));
    }

    public static RationItemEntity toEntity(RationItem rationItem) {
        return new RationItemEntity(rationItem.getId(), rationItem.getMealType(),
                toEntity(rationItem.getDish()));
    }

    public static Dish toModel(DishEntity dishEntity) {
        return new Dish(dishEntity.getId(),
                dishEntity.getName(),
                toModel(dishEntity.getRecipe()),
                dishEntity.getMealTypes(),
                new ArrayList<>(dishEntity.getDishIngredients().stream().map(EntityModelMapper::toModel).toList()));
    }

    public static DishEntity toEntity(Dish dish) {
        return new DishEntity(
                dish.getId(),
                dish.getName(),
                toEntity(dish.getRecipe()),
                dish.getMealTypes(),
                new ArrayList<>(dish.getIngredients().stream().map(EntityModelMapper::toEntity).toList())
        );
    }

    public static DailyRation toModel(DailyRationEntity dailyRationEntity) {
        return new DailyRation(
                dailyRationEntity.getId(),
                dailyRationEntity.getDate(),
                dailyRationEntity.getRationItems().stream().map(EntityModelMapper::toModel).toList()
        );
    }

    public static DailyRationEntity toEntity(DailyRation dailyRation) {
        return new DailyRationEntity(
                dailyRation.getId(),
                dailyRation.getDate(),
                dailyRation.getRationItems().stream().map(EntityModelMapper::toEntity).toList()
        );
    }

    public static Menu toModel(MenuEntity menuEntity) {
        return new Menu(
                menuEntity.getId(),
                menuEntity.getStartDate(),
                menuEntity.getEndDate(),
                menuEntity.getDailyRations().stream().map(EntityModelMapper::toModel).toList()
        );
    }

    public static MenuEntity toEntity(Menu menu) {
        return new MenuEntity(
                menu.getId(),
                menu.getStartDate(),
                menu.getEndDate(),
                menu.getRations().stream().map(EntityModelMapper::toEntity).toList()
        );
    }
}
