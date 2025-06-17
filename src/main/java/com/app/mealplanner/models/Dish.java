package com.app.mealplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Dish {
    private Long id;
    private String name;
    private Recipe recipe;
    private Set<ServiceMealType> mealTypes;
    private List<DishIngredient> ingredients;
}
