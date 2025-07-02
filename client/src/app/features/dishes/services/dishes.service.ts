import { Injectable } from "@angular/core";
import { Dish } from "../../../core/models/dish.model";
import { DishesDataService } from "../../../core/services/dishes-data.service";
import { Ingredient } from "../../../core/models/ingredient.model";
import { IngredientsDataService } from "../../../core/services/ingredients-data.service";

@Injectable()
export class DishesService {

   constructor(
      private readonly dishesDataService: DishesDataService,
      private readonly ingredientsDataService: IngredientsDataService
   ) { }

   dishes: Dish[] = [];
   ingredients: Ingredient[] = [];

   async refresh(): Promise<void> {
      const [ingredients, dishes] = await Promise.all([
         this.ingredientsDataService.getIngredients(),
         this.dishesDataService.getDishes()
      ]);
      this.ingredients = ingredients;
      this.dishes = dishes;
   }

   async deleteDish(id: Dish["id"]): Promise<void> {
      await this.dishesDataService.deleteDish(id)
      this.dishes = this.dishes.filter(i => i.id !== id);
   }

   async addDish(dish: Dish): Promise<void> {
      const createdDish: Dish = await this.dishesDataService.addDish(dish);
      this.dishes = [... this.dishes, createdDish]
   }

   async editDish(dish: Dish): Promise<void> {
      let editedDish: Dish = await this.dishesDataService.editDish(dish);
      this.dishes = this.dishes.map(d => d.id === editedDish.id ? {
         ...d,
         name: editedDish.name,
         recipeDescription: editedDish.recipeDescription,
         recipeExternalLink: editedDish.recipeExternalLink,
         mealTypes: editedDish.mealTypes,
         dishIngredients: editedDish.dishIngredients
      } : d)
   }
}
