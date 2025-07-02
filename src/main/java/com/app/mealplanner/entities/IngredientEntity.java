package com.app.mealplanner.entities;

import com.app.mealplanner.common.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(unique = true, length = 50)
    private String name;

    @ElementCollection(targetClass = Unit.class)
    @CollectionTable(
            name = "measurement_units",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"ingredient_id", "unit"})
    )
    @Column(name = "unit", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Unit> possibleUnits = new HashSet<>();
}
