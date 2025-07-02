package com.app.mealplanner.services;

import com.app.mealplanner.common.enums.MealType;
import com.app.mealplanner.entities.DishEntity;
import com.app.mealplanner.entities.DishIngredientEntity;
import com.app.mealplanner.entities.RecipeEntity;
import com.app.mealplanner.mappers.EntityModelMapper;
import com.app.mealplanner.models.Dish;
import com.app.mealplanner.models.DishIngredient;
import com.app.mealplanner.repositories.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {

    private DishRepository dishRepository;

    @Override
    @Transactional
    public Dish create(String name, String recipeDescription, String recipeExternalLink,
                       Set<MealType> mealTypes, List<DishIngredient> dishIngredients) {
        validateDishName(name);

        DishEntity dishEntity = new DishEntity();
        dishEntity.setName(name);

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setDescription(recipeDescription);
        recipeEntity.setExternalLink(recipeExternalLink);
        dishEntity.setRecipe(recipeEntity);

        dishEntity.setMealTypes(mealTypes);

        List<DishIngredientEntity> dishIngredientEntities = dishIngredients.stream().map(EntityModelMapper::toEntity).toList();
        dishEntity.setDishIngredients(dishIngredientEntities);

        DishEntity savedDishEntity = dishRepository.save(dishEntity);
        return EntityModelMapper.toModel(savedDishEntity);
    }

    @Override
    public Dish get(Long id) {
        DishEntity dishEntity = findDishInDb(id);
        return EntityModelMapper.toModel(dishEntity);
    }

    @Override
    public List<Dish> getALl() {
        return dishRepository.findAll().stream().map(EntityModelMapper::toModel).toList();
    }

    @Override
    @Transactional
    public Dish update(Dish updatedDish) {
        validateDishName(updatedDish.getName());

        DishEntity existingDishEntity = findDishInDb(updatedDish.getId());

        existingDishEntity.setName(updatedDish.getName());
        existingDishEntity.getRecipe().setDescription(updatedDish.getRecipe().getDescription());
        existingDishEntity.getRecipe().setExternalLink(updatedDish.getRecipe().getExternalLink());
        existingDishEntity.setMealTypes(updatedDish.getMealTypes());

        List<DishIngredientEntity> updatedIngredients = updatedDish.getIngredients().stream()
                .map(EntityModelMapper::toEntity)
                .toList();
        existingDishEntity.setDishIngredients(updatedIngredients);

        DishEntity savedDishEntity = dishRepository.save(existingDishEntity);
        return EntityModelMapper.toModel(savedDishEntity);
    }

    @Override
    public void delete(Long id) {
        DishEntity dishEntity = findDishInDb(id);
        dishRepository.delete(dishEntity);
    }

    private DishEntity findDishInDb(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish with id = " + id + " was not found"));
    }

    private void validateDishName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name of dish cannot be null or empty");
        }
    }
}
