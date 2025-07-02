package com.app.mealplanner.controllers;

import com.app.mealplanner.dtos.dishes.DishCreateDto;
import com.app.mealplanner.dtos.dishes.DishDto;
import com.app.mealplanner.dtos.dishes.DishIngredientCreateDto;
import com.app.mealplanner.mappers.ModelDtoMapper;
import com.app.mealplanner.models.Dish;
import com.app.mealplanner.models.DishIngredient;
import com.app.mealplanner.services.DishService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@AllArgsConstructor
public class DishController {
    private DishService dishService;

    @GetMapping
    public List<DishDto> getDishes() {
        return dishService.getALl().stream().map(ModelDtoMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public DishDto getDish(@PathVariable Long id) {
        return ModelDtoMapper.toDto(dishService.get(id));
    }

    @PostMapping
    public ResponseEntity<DishDto> create(@RequestBody DishCreateDto dishCreateDto) {
        List<DishIngredient> dishIngredients = new ArrayList<>();
        for (DishIngredientCreateDto dishIngredientDto : dishCreateDto.getDishIngredients()) {
            DishIngredient dishIngredient = new DishIngredient();
            dishIngredient.setIngredient(ModelDtoMapper.toModel(dishIngredientDto.getIngredientDto()));
            dishIngredient.setQuantity(dishIngredientDto.getQuantity());
            dishIngredient.setUnit(dishIngredientDto.getUnit());
            dishIngredient.setComment(dishIngredientDto.getComment());
            dishIngredients.add(dishIngredient);
        }

        Dish createdDish = dishService.create(dishCreateDto.getName(), dishCreateDto.getRecipeDescription(),
                dishCreateDto.getRecipeExternalLink(), dishCreateDto.getMealTypes(), dishIngredients);

        DishDto createdDishDto = ModelDtoMapper.toDto(createdDish);

        URI location = URI.create(String.format("/api/v1/ingredients/%d", createdDishDto.getId()));
        return ResponseEntity.created(location).body(createdDishDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dishService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DishDto> update(@PathVariable Long id, @RequestBody DishDto updateRequest) {
        Dish dish = dishService.update(ModelDtoMapper.toModel(updateRequest));
        DishDto dishDto = ModelDtoMapper.toDto(dish);
        return ResponseEntity.ok(dishDto);
    }
}
