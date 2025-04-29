package com.app.mealplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dishes")
@Data
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private List<DishIngredient> ingredients;

    @ElementCollection(targetClass = MealType.class)
    @CollectionTable(
            name = "dish_meal_types",
            joinColumns = @JoinColumn(name = "dish_id")
    )
    @Column(name = "meal_type", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<MealType> mealTypes = new HashSet<>();
}
