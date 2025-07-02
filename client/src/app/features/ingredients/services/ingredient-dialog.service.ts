import { Injectable, Injector } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";

import { firstValueFrom } from "rxjs";

import { IngredientModificationDialogComponent } from "../components/ingredient-modification-dialog/ingredient-modification-dialog.component";
import { Ingredient } from "../../../core/models/ingredient.model";

@Injectable()
export class IngredientDialogService {

   constructor(
      private readonly dialog: MatDialog,
      private readonly injector: Injector,
   ) { }

   openIngredientModificationDialog(ingredient?: Ingredient): void {
     this.dialog.open<IngredientModificationDialogComponent, Ingredient | undefined, number>(IngredientModificationDialogComponent, {
         width: "100rem",
         injector: this.injector,
         data: ingredient,
      });
   }
}
