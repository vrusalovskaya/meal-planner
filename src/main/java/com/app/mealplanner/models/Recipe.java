package com.app.mealplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Recipe {
    private Long id;
    private String description;
    private String externalLink;
}
