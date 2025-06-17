package com.app.mealplanner.services;

import com.app.mealplanner.models.Ingredient;

import java.util.List;

public interface IngredientService {
    Long create(String name);

    List<Ingredient> getAll();

    void update(Long id, String newName);

    void delete(Long id);
}
