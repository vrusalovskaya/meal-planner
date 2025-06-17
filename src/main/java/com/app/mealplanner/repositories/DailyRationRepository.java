package com.app.mealplanner.repositories;

import com.app.mealplanner.entities.DailyRationEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface DailyRationRepository extends ListCrudRepository<DailyRationEntity, Long> {
}
