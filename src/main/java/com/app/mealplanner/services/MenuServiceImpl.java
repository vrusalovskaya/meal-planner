package com.app.mealplanner.services;

import com.app.mealplanner.entities.DailyRationEntity;
import com.app.mealplanner.entities.DishEntity;
import com.app.mealplanner.entities.MenuEntity;
import com.app.mealplanner.entities.RationItemEntity;
import com.app.mealplanner.mappers.EntityModelMapper;
import com.app.mealplanner.models.DailyRation;
import com.app.mealplanner.models.Menu;
import com.app.mealplanner.models.RationItem;
import com.app.mealplanner.repositories.DishRepository;
import com.app.mealplanner.repositories.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepository;
    private DishRepository dishRepository;

    @Override
    @Transactional
    public Menu create(LocalDate startDate, LocalDate endDate, List<DailyRation> rations) {
        validateDates(null, startDate, endDate);
        validateMealTypes(rations);
        List<DailyRationEntity> dailyRationEntities = EntityModelMapper.toCreateEntities(rations);

        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setStartDate(startDate);
        menuEntity.setEndDate(endDate);

        menuEntity.setDailyRations(dailyRationEntities);
        MenuEntity savedMenuEntity = menuRepository.save(menuEntity);
        return EntityModelMapper.toModel(savedMenuEntity);
    }

    @Override
    public Menu get(Long id) {
        MenuEntity menuEntity = findMenuInDb(id);
        return EntityModelMapper.toModel(menuEntity);
    }

    @Override
    public Optional<Menu> getCurrent() {
        LocalDate now = LocalDate.now();
        return Optional.ofNullable(menuRepository.findCurrentMenu(now))
                .map(EntityModelMapper::toModel);
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.findAll().stream().map(EntityModelMapper::toModel).toList();
    }

    @Override
    public List<Menu> getPast() {
        LocalDate now = LocalDate.now();
        return menuRepository.findPastMenus(now).stream().map(EntityModelMapper::toModel).toList();
    }

    @Override
    public List<Menu> getFuture() {
        LocalDate now = LocalDate.now();
        return menuRepository.findFutureMenus(now).stream().map(EntityModelMapper::toModel).toList();
    }

    @Override
    public Menu update(Menu updatedMenu) {
        validateDates(updatedMenu.getId(), updatedMenu.getStartDate(), updatedMenu.getEndDate());
        validateMealTypes(updatedMenu.getRations());

        MenuEntity existingMenu = findMenuInDb(updatedMenu.getId());

        boolean datesChanged = !existingMenu.getStartDate().equals(updatedMenu.getStartDate())
                || !existingMenu.getEndDate().equals(updatedMenu.getEndDate());
        List<DailyRationEntity> updatedDailyRationEntities;

        if (datesChanged) {
            updatedDailyRationEntities = EntityModelMapper.toCreateEntities(updatedMenu.getRations());
        } else {
            updatedDailyRationEntities = updatedMenu.getRations().stream()
                    .map(EntityModelMapper::toEntity).toList();
        }

        existingMenu.setStartDate(updatedMenu.getStartDate());
        existingMenu.setEndDate(updatedMenu.getEndDate());

        existingMenu.getDailyRations().clear();
        existingMenu.getDailyRations().addAll(updatedDailyRationEntities);

        MenuEntity savedMenu = menuRepository.save(existingMenu);
        return EntityModelMapper.toModel(savedMenu);
    }

    @Override
    public Menu updateCurrent(Long menuId, Map<Long, Long> updatedDishes) {
        MenuEntity existingMenu = findMenuInDb(menuId);

        Set<Long> existingRationItemIds = existingMenu.getDailyRations()
                .stream()
                .flatMap(ration -> ration.getRationItems().stream())
                .map(RationItemEntity::getId)
                .collect(Collectors.toSet());

        for (Long rationItemId : updatedDishes.keySet()) {
            if (!existingRationItemIds.contains(rationItemId)) {
                throw new IllegalArgumentException("RationItem with id = " + rationItemId + " does not belong to menu " + menuId);
            }
        }

        Set<Long> dishIdsToFetch = new HashSet<>();
        for (DailyRationEntity ration : existingMenu.getDailyRations()) {
            for (RationItemEntity rationItem : ration.getRationItems()) {
                Long updatedDishId = updatedDishes.get(rationItem.getId());
                if (updatedDishId != null && !updatedDishId.equals(rationItem.getDish().getId())) {
                    dishIdsToFetch.add(updatedDishId);
                }
            }
        }

        Map<Long, DishEntity> dishMap = dishRepository.findAllById(dishIdsToFetch)
                .stream()
                .collect(Collectors.toMap(DishEntity::getId, d -> d));

        for (DailyRationEntity ration : existingMenu.getDailyRations()) {
            for (RationItemEntity rationItem : ration.getRationItems()) {
                Long updatedDishId = updatedDishes.get(rationItem.getId());

                if (updatedDishId == null || updatedDishId.equals(rationItem.getDish().getId())) {
                    continue;
                }

                DishEntity updatedDish = dishMap.get(updatedDishId);
                if (updatedDish == null) {
                    throw new EntityNotFoundException("Dish with id = " + updatedDishId + " was not found");
                }

                if (!updatedDish.getMealTypes().contains(rationItem.getMealType())) {
                    throw new IllegalArgumentException(
                            "Dish '" + updatedDish.getName() + "' cannot be selected for " +
                                    rationItem.getMealType() + ". Possible: " + updatedDish.getMealTypes() +
                                    ". RationItem ID: " + rationItem.getId()
                    );
                }

                rationItem.setDish(updatedDish);
            }
        }

        MenuEntity savedMenu = menuRepository.save(existingMenu);
        return EntityModelMapper.toModel(savedMenu);
    }

    @Override
    public void delete(Long id) {
        //investigate deleteById vs delete
        MenuEntity menuEntity = findMenuInDb(id);
        menuRepository.delete(menuEntity);
    }

    private MenuEntity findMenuInDb(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu with id = " + id + " was not found"));
    }

    void validateDates(Long id, LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (now.isAfter(startDate)) {
            throw new IllegalArgumentException(
                    "Menu start (" + startDate + ") must not be in the past (now is " + now + ")");
        }

        List<Menu> futureMenus = menuRepository.findMenusEndingNowOrLater(now)
                .stream().map(EntityModelMapper::toModel).toList();

        boolean conflict = futureMenus.stream().anyMatch(m ->
                !Objects.equals(m.getId(), id) && !m.getStartDate().isAfter(endDate) && !m.getEndDate().isBefore(startDate)
        );

        if (conflict) {
            throw new IllegalStateException(
                    "The window [" + startDate + " → " + endDate + "] overlaps an existing menu.");
        }
    }

    private void validateMealTypes(List<DailyRation> dailyRations) {
        for (DailyRation dailyRation : dailyRations) {
            for (RationItem rationItem : dailyRation.getRationItems()) {
                if (!rationItem.getDish().getMealTypes().contains(rationItem.getMealType())) {
                    throw new IllegalArgumentException(
                            "Dish '" + rationItem.getDish().getName() + "' cannot be selected for " +
                                    rationItem.getMealType() + ". Possible: " + rationItem.getDish().getMealTypes() +
                                    ". RationItem ID: " + rationItem.getId());
                }
            }
        }
    }
}
