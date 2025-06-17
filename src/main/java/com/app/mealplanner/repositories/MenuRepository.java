package com.app.mealplanner.repositories;

import com.app.mealplanner.entities.MenuEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface MenuRepository extends ListCrudRepository<MenuEntity, Long> {
}
