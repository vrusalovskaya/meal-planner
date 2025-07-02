package com.app.mealplanner.models;

import com.app.mealplanner.common.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Ingredient {
    private Long id;
    private String name;
    private Set<Unit> possibleUnits;
}
