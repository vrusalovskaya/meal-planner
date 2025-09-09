package com.app.mealplanner.services;

import com.app.mealplanner.models.DailyRation;
import com.app.mealplanner.models.Dish;
import com.app.mealplanner.models.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MenuService {
    Menu create(LocalDate startDate, LocalDate endDate, List<DailyRation> rations);

    Menu get(Long id);

    Optional<Menu> getCurrent();

    List<Menu> getAll();

    List<Menu> getPast();

    List<Menu> getFuture();

    Menu update(Menu updatedMenu);

    Menu updateCurrent(Long menuId, Map<Long, Long> updatedDishes);

    void delete(Long id);
}
