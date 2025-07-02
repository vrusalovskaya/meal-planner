package com.app.mealplanner.dtos.ingredients;

import com.app.mealplanner.common.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class IngredientCreateRequest {
    private String name;
    private Set<Unit> possibleUnits;
}
