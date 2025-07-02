import { Ingredient } from "./ingredient.model";

export interface DishIngredient {
   id: number | null;
   ingredient: Ingredient;
   quantity: number;
   unit: string;
   comment: string;
}
