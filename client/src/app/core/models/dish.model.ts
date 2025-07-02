import { DishIngredient } from "./dish-ingredient.model";
import { MealType } from "./meal-type.model";

export interface Dish{
   id: number;
   name: string;
   recipeDescription: string;
   recipeExternalLink: string;
   mealTypes: MealType[];
   dishIngredients: DishIngredient[];
}
