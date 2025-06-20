package com.app.mealplanner.controllers;

import com.app.mealplanner.dtos.ingredients.IngredientCreateRequest;
import com.app.mealplanner.dtos.ingredients.IngredientDto;
import com.app.mealplanner.dtos.ingredients.IngredientUpdateRequest;
import com.app.mealplanner.mappers.ModelDtoMapper;
import com.app.mealplanner.services.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Void> create(IngredientCreateRequest createRequest) {
        Long id = ingredientService.create(createRequest.getName());
        URI location = URI.create(String.format("/api/v1/ingredients/%d", id));
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, IngredientUpdateRequest updateRequest) {
        ingredientService.update(id, updateRequest.getName());
        return ResponseEntity.ok("Ingredient name was updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
