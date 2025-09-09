package com.app.mealplanner.mappers;

import com.app.mealplanner.dtos.menus.DailyRationCreateDto;
import com.app.mealplanner.dtos.menus.DailyRationDto;
import com.app.mealplanner.dtos.menus.RationItemCreateDto;
import com.app.mealplanner.dtos.menus.RationItemDto;
import com.app.mealplanner.dtos.dishes.DishDto;
import com.app.mealplanner.dtos.dishes.DishIngredientDto;
import com.app.mealplanner.dtos.ingredients.IngredientDto;
import com.app.mealplanner.dtos.menus.MenuDto;
import com.app.mealplanner.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModelDtoMapper {
    public static IngredientDto toDto(Ingredient ingredient) {
        return new IngredientDto(ingredient.getId(), ingredient.getName(), ingredient.getPossibleUnits());
    }

    public static Ingredient toModel(IngredientDto ingredientDto) {
        return new Ingredient(ingredientDto.getId(), ingredientDto.getName(), ingredientDto.getPossibleUnits());
    }

    public static DishIngredientDto toDto(DishIngredient dishIngredient) {
        return new DishIngredientDto(dishIngredient.getId(), toDto(dishIngredient.getIngredient()), dishIngredient.getQuantity(),
                dishIngredient.getUnit(), dishIngredient.getComment());
    }

    public static DishIngredient toModel(DishIngredientDto dishIngredientDto) {
        return new DishIngredient(dishIngredientDto.getId(), toModel(dishIngredientDto.getIngredient()),
                dishIngredientDto.getQuantity(), dishIngredientDto.getUnit(), dishIngredientDto.getComment());
    }

    public static DishDto toDto(Dish dish) {
        return new DishDto(dish.getId(), dish.getName(), dish.getRecipe().getDescription(),
                dish.getRecipe().getExternalLink(), dish.getMealTypes(),
                new ArrayList<>(dish.getIngredients().stream().map(ModelDtoMapper::toDto).toList()));
    }

    public static Dish toModel(DishDto dishDto) {
        Recipe recipe = new Recipe();
        recipe.setDescription(dishDto.getRecipeDescription());
        recipe.setExternalLink(dishDto.getRecipeExternalLink());
        return new Dish(dishDto.getId(), dishDto.getName(), recipe, dishDto.getMealTypes(),
                new ArrayList<>(dishDto.getDishIngredients().stream().map(ModelDtoMapper::toModel).toList()));
    }

    public static RationItemDto toDto(RationItem rationItem) {
        return new RationItemDto(rationItem.getId(), rationItem.getMealType(), toDto(rationItem.getDish()));
    }

    public static RationItem toModel(RationItemDto rationItemDto) {
        return new RationItem(rationItemDto.getId(), rationItemDto.getMealType(), toModel(rationItemDto.getDish()));
    }

    public static DailyRationDto toDto(DailyRation dailyRation) {
        return new DailyRationDto(dailyRation.getId(), dailyRation.getDate().toString(),
                new ArrayList<>(dailyRation.getRationItems().stream().map(ModelDtoMapper::toDto).toList()));
    }

    public static DailyRation toModel(DailyRationDto dailyRationDto) {
        return new DailyRation(dailyRationDto.getId(), LocalDate.parse(dailyRationDto.getDate()),
                new ArrayList<>(dailyRationDto.getRationItems().stream().map(ModelDtoMapper::toModel).toList()));
    }

    public static MenuDto toDto(Menu menu) {
        return new MenuDto(menu.getId(), menu.getStartDate().toString(), menu.getEndDate().toString(),
                new ArrayList<>(menu.getRations().stream().map(ModelDtoMapper::toDto).toList()));
    }

    public static Menu toModel(MenuDto menuDto) {
        return new Menu(menuDto.getId(), LocalDate.parse(menuDto.getStartDate()), LocalDate.parse(menuDto.getEndDate()),
                new ArrayList<>(menuDto.getRations().stream().map(ModelDtoMapper::toModel).toList()));
    }

    public static List<DailyRation> toCreateModels(List<DailyRationCreateDto> createDtos) {
        List<DailyRation> dailyRations = new ArrayList<>();

        for (DailyRationCreateDto ration : createDtos) {
            DailyRation dailyRation = new DailyRation();
            dailyRation.setDate(ration.getDate());

            List<RationItem> rationItems = new ArrayList<>();
            for (RationItemCreateDto rationItem : ration.getRationItems()) {
                RationItem rItem = new RationItem();
                rItem.setMealType(rationItem.getMealType());
                rItem.setDish(toModel(rationItem.getDish()));
                rationItems.add(rItem);
            }

            dailyRation.setRationItems(rationItems);
            dailyRations.add(dailyRation);
        }

        return dailyRations;
    }
}
