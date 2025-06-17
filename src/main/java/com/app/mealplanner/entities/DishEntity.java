package com.app.mealplanner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private RecipeEntity recipe;

    @ElementCollection(targetClass = PersistenceMealType.class)
    @CollectionTable(
            name = "dish_meal_types",
            joinColumns = @JoinColumn(name = "dish_id")
    )
    @Column(name = "meal_type", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PersistenceMealType> mealTypes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private List<DishIngredientEntity> dishIngredients;
}
