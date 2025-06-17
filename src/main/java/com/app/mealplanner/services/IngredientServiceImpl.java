package com.app.mealplanner.services;

import com.app.mealplanner.entities.IngredientEntity;
import com.app.mealplanner.mappers.EntityModelMapper;
import com.app.mealplanner.models.Ingredient;
import com.app.mealplanner.repositories.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Override
    public Long create(String name) {
        validateName(name);

        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName(name);
        IngredientEntity savedIngredient = ingredientRepository.save(ingredientEntity);
        return savedIngredient.getId();
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll().stream().map(EntityModelMapper::toModel).toList();
    }

    @Override
    @Transactional
    public void update(Long id, String newName) {
        validateName(newName);

        IngredientEntity ingredientEntity = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient with id = " + id + " was not found"));
        ingredientEntity.setName(newName);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        IngredientEntity ingredientEntity = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient with id = " + id + " was not found"));
        ingredientRepository.delete(ingredientEntity);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name of ingredient cannot be null or empty");
        }
    }
}
