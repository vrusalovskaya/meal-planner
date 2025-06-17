package com.app.mealplanner.dtos.ingredients;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientCreateRequest {
    private String name;
}
