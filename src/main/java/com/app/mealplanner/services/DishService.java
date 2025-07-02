package com.app.mealplanner.services;

import com.app.mealplanner.common.enums.MealType;
import com.app.mealplanner.models.Dish;
import com.app.mealplanner.models.DishIngredient;

import java.util.List;
import java.util.Set;

public interface DishService {
    Dish create(String name, String recipeDescription, String recipeExternalLink, Set<MealType> mealTypes,
                List<DishIngredient> dishIngredients);

    Dish get(Long id);

    List<Dish> getALl();

    Dish update(Dish updatedDish);

    void delete(Long id);
}
