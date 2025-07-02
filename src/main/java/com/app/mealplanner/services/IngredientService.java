package com.app.mealplanner.services;

import com.app.mealplanner.common.enums.Unit;
import com.app.mealplanner.models.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientService {
    Ingredient create(String name, Set<Unit> possibleUnits);

    List<Ingredient> getAll();

    Ingredient update(Long id, String newName, Set<Unit> possibleUnits);

    void delete(Long id);
}
