package com.app.mealplanner.entities;

import com.app.mealplanner.common.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dish_ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private IngredientEntity ingredient;

    @Positive
    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", length = 15, nullable = false)
    private Unit unit;

    @Size(max = 255)
    private String comment;
}
