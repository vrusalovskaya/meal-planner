package com.app.mealplanner.controllers;

import com.app.mealplanner.common.enums.Unit;
import com.app.mealplanner.dtos.ingredients.IngredientCreateRequest;
import com.app.mealplanner.dtos.ingredients.IngredientDto;
import com.app.mealplanner.dtos.ingredients.IngredientUpdateRequest;
import com.app.mealplanner.mappers.ModelDtoMapper;
import com.app.mealplanner.models.Ingredient;
import com.app.mealplanner.services.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
@AllArgsConstructor
public class IngredientController {
    private IngredientService ingredientService;

    @GetMapping
    public List<IngredientDto> getIngredients() {
        return ingredientService.getAll().stream().map(ModelDtoMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<IngredientDto> create(@RequestBody IngredientCreateRequest createRequest) {
        Ingredient ingredient = ingredientService.create(createRequest.getName(), createRequest.getPossibleUnits());
        IngredientDto ingredientDto = ModelDtoMapper.toDto(ingredient);

        URI location = URI.create(String.format("/api/v1/ingredients/%d", ingredientDto.getId()));
        return ResponseEntity.created(location).body(ingredientDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IngredientDto> update(@PathVariable Long id, @RequestBody IngredientUpdateRequest updateRequest) {
        Ingredient ingredient = ingredientService.update(id, updateRequest.getName(), updateRequest.getPossibleUnits());
        IngredientDto ingredientDto = ModelDtoMapper.toDto(ingredient);
        return ResponseEntity.ok(ingredientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/units")
    public List<Unit> getUnits() {
        return Arrays.stream(Unit.values()).toList();
    }
}
