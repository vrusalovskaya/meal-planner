import { Dish } from "./dish.model";
import { MealType } from "./meal-type.model";

export interface RationItem{
   id: number;
   mealType: MealType;
   dish: Dish;
}