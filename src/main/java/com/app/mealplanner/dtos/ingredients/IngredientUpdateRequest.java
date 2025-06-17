package com.app.mealplanner.dtos.ingredients;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientUpdateRequest {
    private String name;
}
