import { Injectable } from "@angular/core";

import { IngredientsDataService } from "../../../core/services/ingredients-data.service";
import { Ingredient } from "../../../core/models/ingredient.model";

@Injectable()
export class IngredientsService {

   constructor(
      private readonly ingredientsDataService: IngredientsDataService
   ) { }

   ingredients: Ingredient[] = [];

   async refresh(): Promise<void> {
      this.ingredients = await this.ingredientsDataService.getIngredients();
   }

   async deleteIngredient(id: Ingredient["id"]): Promise<void> {
      await this.ingredientsDataService.deleteIngredient(id)
      this.ingredients = this.ingredients.filter(i => i.id !== id);
   }
}
