import { Injectable } from "@angular/core";

import { IngredientsDataService } from "../../../core/services/ingredients-data.service";
import { Ingredient } from "../../../core/models/ingredient.model";

@Injectable()
export class IngredientsService {

   constructor(
      private readonly ingredientsDataService: IngredientsDataService
   ) { }

   ingredients: Ingredient[] = [];
   units: string[] = [];

   async refresh(): Promise<void> {
      const [ingredients, units] = await Promise.all([
         this.ingredientsDataService.getIngredients(),
         this.ingredientsDataService.getUnits()
      ]);
      this.ingredients = ingredients;
      this.units = units;
   }

   async deleteIngredient(id: Ingredient["id"]): Promise<void> {
      await this.ingredientsDataService.deleteIngredient(id)
      this.ingredients = this.ingredients.filter(i => i.id !== id);
   }

   async addIngredient(ingredient: Ingredient): Promise<void> {
      let createdIngredient: Ingredient = await this.ingredientsDataService.addIngredient(ingredient);
      this.ingredients = [...this.ingredients, createdIngredient];
   }

   async editIngredient(ingredient: Ingredient): Promise<void> {
      let editedIngredient: Ingredient = await this.ingredientsDataService.editIngredient(ingredient);
      this.ingredients = this.ingredients.map(i => i.id === editedIngredient.id ? { ...i, name: editedIngredient.name, possibleUnits: editedIngredient.possibleUnits } : i)
   }
}
