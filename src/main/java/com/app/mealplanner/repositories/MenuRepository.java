package com.app.mealplanner.repositories;

import com.app.mealplanner.entities.MenuEntity;
import com.app.mealplanner.models.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends ListCrudRepository<MenuEntity, Long> {
    @Query("SELECT m FROM MenuEntity m WHERE m.endDate >= :now")
    List<MenuEntity> findMenusEndingNowOrLater(@Param("now") LocalDate now);

    @Query("SELECT m FROM MenuEntity m WHERE m.endDate < :now")
    List<MenuEntity> findPastMenus(@Param("now") LocalDate now);

    @Query("SELECT m FROM MenuEntity m WHERE m.startDate > :now")
    List<MenuEntity> findFutureMenus(@Param("now") LocalDate now);

    @Query("SELECT m FROM MenuEntity m WHERE m.endDate >= :now AND m.startDate<= :now")
    MenuEntity findCurrentMenu(@Param("now") LocalDate now);
}
