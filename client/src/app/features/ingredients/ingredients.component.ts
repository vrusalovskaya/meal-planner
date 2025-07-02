import { Component } from "@angular/core";

import { IngredientListComponent } from "./components/ingredient-list/ingredient-list.component";
import { IngredientsService } from "./services/ingredients.service";
import { Ingredient } from "../../core/models/ingredient.model";
import { DialogService } from "../../core/services/dialog.service";
import { IngredientDialogService } from "./services/ingredient-dialog.service";
import { MatButton } from "@angular/material/button";

@Component({
   selector: "mp-ingredients",
   imports: [
      IngredientListComponent,
      MatButton
   ],
   templateUrl: "./ingredients.component.html",
   styleUrl: "./ingredients.component.scss",
})
export class IngredientsComponent {
   constructor(
      private readonly ingredientsService: IngredientsService,
      private readonly dialogService: DialogService,
      private readonly ingredientsDialogService: IngredientDialogService
   ) { }

   get ingredients(): Ingredient[] { return this.ingredientsService.ingredients };

   onIngredientDelete(ingredient: Ingredient) {

      this.dialogService.openConfirmationDialog({
         cancelButtonText: "Cancel",
         confirmButtonText: "Yes, delete",
         content: "Are you sure you want to delete this ingredient?",
         title: "Delete Ingredient",
         confirmAction: () => this.ingredientsService.deleteIngredient(ingredient.id)
      });
   }

   onAddIngredientClicked() {
      this.ingredientsDialogService.openIngredientModificationDialog();
   }

   onIngredientEdit(ingredient: Ingredient) {
      this.ingredientsDialogService.openIngredientModificationDialog(ingredient);
   }
}
