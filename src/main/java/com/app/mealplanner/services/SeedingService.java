package com.app.mealplanner.services;

import com.app.mealplanner.common.enums.MealType;
import com.app.mealplanner.common.enums.Unit;
import com.app.mealplanner.entities.*;
import com.app.mealplanner.repositories.DishRepository;
import com.app.mealplanner.repositories.IngredientRepository;
import com.app.mealplanner.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SeedingService {
    private final IngredientRepository ingredientRepository;
    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void seed() {
        if (ingredientRepository.count() != 0 || dishRepository.count() != 0 || menuRepository.count() != 0) {
            System.out.println("Data already exists. Skipping full seeding.");
            return;
        }

        List<IngredientEntity> ingredients = List.of(
                new IngredientEntity(null, "Salt", Set.of(Unit.TSP, Unit.PINCH)),
                new IngredientEntity(null, "Sugar", Set.of(Unit.G, Unit.TBSP, Unit.CUP)),
                new IngredientEntity(null, "Flour", Set.of(Unit.G, Unit.KG, Unit.CUP)),
                new IngredientEntity(null, "Egg", Set.of(Unit.PC)),
                new IngredientEntity(null, "Milk", Set.of(Unit.ML, Unit.L, Unit.CUP)),
                new IngredientEntity(null, "Butter", Set.of(Unit.G, Unit.TBSP)),
                new IngredientEntity(null, "Olive Oil", Set.of(Unit.ML, Unit.TBSP, Unit.TSP)),
                new IngredientEntity(null, "Garlic", Set.of(Unit.PC, Unit.NO_MEASUREMENT)),
                new IngredientEntity(null, "Onion", Set.of(Unit.PC)),
                new IngredientEntity(null, "Tomato", Set.of(Unit.PC)),
                new IngredientEntity(null, "Basil", Set.of(Unit.G, Unit.PINCH, Unit.NO_MEASUREMENT)),
                new IngredientEntity(null, "Black Pepper", Set.of(Unit.PINCH, Unit.TSP)),
                new IngredientEntity(null, "Cheese", Set.of(Unit.G, Unit.CUP)),
                new IngredientEntity(null, "Chicken Breast", Set.of(Unit.G, Unit.KG, Unit.PC)),
                new IngredientEntity(null, "Rice", Set.of(Unit.G, Unit.CUP)),
                new IngredientEntity(null, "Carrot", Set.of(Unit.PC, Unit.G)),
                new IngredientEntity(null, "Potato", Set.of(Unit.PC, Unit.G)),
                new IngredientEntity(null, "Lemon Juice", Set.of(Unit.ML, Unit.TBSP, Unit.TSP)),
                new IngredientEntity(null, "Water", Set.of(Unit.ML, Unit.L, Unit.CUP)),
                new IngredientEntity(null, "Yeast", Set.of(Unit.G, Unit.TSP))
        );

        ingredientRepository.saveAll(ingredients);

        List<String> dishNames = List.of(
                "Pancakes",
                "Omelette",
                "Chicken Curry",
                "Grilled Cheese Sandwich",
                "Tomato Soup",
                "Fried Rice",
                "Spaghetti Bolognese",
                "Mashed Potatoes",
                "Roast Chicken",
                "Garlic Bread",
                "Scrambled Eggs",
                "Vegetable Stir Fry"
        );

        List<DishEntity> dishes = new ArrayList<>();
        Random random = new Random();

        for (String dishName : dishNames) {
            Set<DishIngredientEntity> dishIngredients = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                IngredientEntity ingredient = ingredients.get(random.nextInt(ingredients.size()));
                Unit unit = ingredient.getPossibleUnits().stream().findFirst().orElse(Unit.NO_MEASUREMENT);
                dishIngredients.add(new DishIngredientEntity(null, ingredient, 1.0, unit, null));
            }

            RecipeEntity recipe = new RecipeEntity();
            recipe.setDescription("Delicious " + dishName.toLowerCase());
            recipe.setExternalLink("https://example.com/" + dishName.toLowerCase().replace(" ", "-"));

            DishEntity dish = new DishEntity();
            dish.setName(dishName);
            dish.setRecipe(recipe);
            dish.setMealTypes(Set.of(MealType.values()[random.nextInt(MealType.values().length)]));
            dish.setDishIngredients(dishIngredients.stream().toList());

            dishes.add(dish);
        }

        dishRepository.saveAll(dishes);

        for (int i = 0; i < 3; i++) {
            LocalDate start = LocalDate.now().plusWeeks(i);
            LocalDate end = start.plusDays(6);
            MenuEntity menu = new MenuEntity();
            menu.setStartDate(start);
            menu.setEndDate(end);

            List<DailyRationEntity> rations = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                LocalDate date = start.plusDays(j);
                DailyRationEntity ration = new DailyRationEntity();
                ration.setDate(date);
                for (MealType mealType : MealType.values()) {
                    RationItemEntity rationItem = new RationItemEntity();
                    rationItem.setMealType(mealType);
                    rationItem.setDish(dishes.get(random.nextInt(dishes.size())));
                    ration.getRationItems().add(rationItem);
                }
                rations.add(ration);
            }
            menu.getDailyRations().addAll(rations);
            menuRepository.save(menu);
        }
    }

    @Transactional
    public void seedMenusOnly() {
        LocalDate now = LocalDate.now();
        if (!menuRepository.findMenusEndingNowOrLater(now).isEmpty()) {
            System.out.println("Future menus already exist. Skipping seeding menus.");
            return;
        }

        List<DishEntity> dishes = dishRepository.findAll();
        if (dishes.isEmpty()) {
            System.out.println("No dishes found. Cannot seed menus.");
            return;
        }

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            LocalDate start = now.plusWeeks(i);
            LocalDate end = start.plusDays(6);

            MenuEntity menu = new MenuEntity();
            menu.setStartDate(start);
            menu.setEndDate(end);

            List<DailyRationEntity> rations = new ArrayList<>();

            for (int j = 0; j < 7; j++) {
                LocalDate date = start.plusDays(j);
                DailyRationEntity ration = new DailyRationEntity();
                ration.setDate(date);

                for (MealType mealType : MealType.values()) {
                    RationItemEntity rationItem = new RationItemEntity();
                    rationItem.setMealType(mealType);
                    rationItem.setDish(dishes.get(random.nextInt(dishes.size())));
                    ration.getRationItems().add(rationItem);
                }
                rations.add(ration);
            }

            menu.getDailyRations().addAll(rations);
            menuRepository.save(menu);
        }

        System.out.println("Menus seeded successfully!");
    }
}
