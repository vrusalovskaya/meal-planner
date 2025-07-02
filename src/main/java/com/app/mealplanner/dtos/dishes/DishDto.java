package com.app.mealplanner.dtos.dishes;

import com.app.mealplanner.common.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class DishDto {
    private Long id;
    private String name;
    private String recipeDescription;
    private String recipeExternalLink;
    private Set<MealType> mealTypes;
    private List<DishIngredientDto> dishIngredients;
}
