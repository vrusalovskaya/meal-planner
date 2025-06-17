package com.app.mealplanner.repositories;

import com.app.mealplanner.entities.IngredientEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface IngredientRepository extends ListCrudRepository<IngredientEntity, Long> {
}
