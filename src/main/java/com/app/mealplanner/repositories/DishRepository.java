package com.app.mealplanner.repositories;

import com.app.mealplanner.entities.DishEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface DishRepository extends ListCrudRepository<DishEntity, Long> {
}
