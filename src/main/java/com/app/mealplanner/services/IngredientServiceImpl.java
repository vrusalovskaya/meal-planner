package com.app.mealplanner.services;

import com.app.mealplanner.common.enums.Unit;
import com.app.mealplanner.entities.IngredientEntity;
import com.app.mealplanner.mappers.EntityModelMapper;
import com.app.mealplanner.models.Ingredient;
import com.app.mealplanner.repositories.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient create(String name, Set<Unit> possibleUnits) {
        validateName(name);

        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName(name);
        ingredientEntity.setPossibleUnits(possibleUnits);
        IngredientEntity savedIngredient = ingredientRepository.save(ingredientEntity);
        return EntityModelMapper.toModel(savedIngredient);
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll().stream().map(EntityModelMapper::toModel).toList();
    }

    @Override
    @Transactional
    public Ingredient update(Long id, String newName, Set<Unit> possibleUnits) {
        validateName(newName);

        IngredientEntity ingredientEntity = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient with id = " + id + " was not found"));
        ingredientEntity.setName(newName);
        ingredientEntity.setPossibleUnits(possibleUnits);
        return EntityModelMapper.toModel(ingredientEntity);
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
