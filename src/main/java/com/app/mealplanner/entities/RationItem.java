package com.app.mealplanner.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ration_items")
public class RationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ration_id", nullable = false)
    private DailyRation ration;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", length = 10, nullable = false)
    private MealType mealType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
}
