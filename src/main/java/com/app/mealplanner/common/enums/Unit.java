package com.app.mealplanner.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Unit {
    NO_MEASUREMENT(0),

    KG(1000),
    G(1),

    ML(1),
    L(1000),

    TBSP(15),
    TSP(5),

    CUP(300),

    PC(1),

    PINCH(1);

    private final Integer value;
}
