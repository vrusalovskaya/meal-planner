package com.app.mealplanner.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ration_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RationItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", length = 10, nullable = false)
    private PersistenceMealType mealType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dish_id", nullable = false)
    private DishEntity dish;
}
