import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from "@angular/core";
import { Ingredient } from "../../../../core/models/ingredient.model";
import { IngredientComponent } from "../ingredient/ingredient.component";

@Component({
   selector: "mp-ingredient-list",
   imports: [IngredientComponent],
   templateUrl: "./ingredient-list.component.html",
   styleUrl: "./ingredient-list.component.scss",
})
export class IngredientListComponent {
   @Input({ required: true }) ingredients: Ingredient[] = [];
   @Output() ingredientDelete = new EventEmitter<Ingredient>();
   @Output() ingredientEdit = new EventEmitter<Ingredient>();

   onIngredientDelete(ingredient: Ingredient) {
      this.ingredientDelete.emit(ingredient);
   }

   onIngredientEdit(ingredient: Ingredient){
      this.ingredientEdit.emit(ingredient);
   }
}
