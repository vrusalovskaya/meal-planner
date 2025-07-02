import { Injectable, Injector } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Ingredient } from '../../../core/models/ingredient.model';
import { IngredientModificationDialogComponent } from '../../ingredients/components/ingredient-modification-dialog/ingredient-modification-dialog.component';
import { Dish } from '../../../core/models/dish.model';
import { DishModificationDialogComponent } from '../components/dish-modification-dialog/dish-modification-dialog.component';

@Injectable()
export class DishDialogService {

    constructor(
      private readonly dialog: MatDialog,
      private readonly injector: Injector,
   ) { }

   openDishModificationDialog(dish?: Dish): void {
     this.dialog.open<DishModificationDialogComponent, Dish | undefined, number>(DishModificationDialogComponent, {
         width: "100rem",
         injector: this.injector,
         data: dish,
      },);
   }

}
