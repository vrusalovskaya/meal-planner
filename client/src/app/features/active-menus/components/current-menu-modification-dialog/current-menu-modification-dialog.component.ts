import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from "@angular/material/dialog";
import { Menu } from "../../../../core/models/menu.model";
import { ActiveMenusService } from "../../services/active-menus.service";
import { FormsModule, NgModel } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { CommonModule } from "@angular/common";
import { MatChipsModule } from "@angular/material/chips";
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from "@angular/material/autocomplete";
import { Dish } from "../../../../core/models/dish.model";
import { RationItem } from "../../../../core/models/ration-item.model";
import { DishEditingComponent } from "../dish-editing/dish-editing.component";
import { MealType } from "../../../../core/models/meal-type.model";

@Component({
   selector: "mp-current-menu-modification-dialog",
   imports: [
      FormsModule,
      MatFormFieldModule,
      MatInputModule,
      MatDialogActions,
      MatDialogClose,
      MatDialogContent,
      MatDialogTitle,
      MatButtonModule,
      MatDialogModule,
      CommonModule,
      MatChipsModule,
      MatAutocompleteModule,
      DishEditingComponent
   ],
   templateUrl: "./current-menu-modification-dialog.component.html",
   styleUrl: "./current-menu-modification-dialog.component.scss",
})
export class CurrentMenuModificationDialogComponent {

   readonly menu: Menu;
   dishes: Dish[];

   constructor(
      private readonly currentMenuService: ActiveMenusService,
      private dialogRef: MatDialogRef<CurrentMenuModificationDialogComponent>,
      @Inject(MAT_DIALOG_DATA) data: Menu
   ) {
      this.menu = { ...data };
      this.dishes = currentMenuService.dishes;
   }

   dishesByMealType(mealType: MealType) {
      return this.currentMenuService.dishes.filter(i => i.mealTypes.includes(mealType));
   }

   onDishUpdate(updatedDish: Dish, rationItem: RationItem) {
      rationItem.dish = updatedDish;
   }

   async save() {
      await this.currentMenuService.editCurrentMenu(this.menu);
      this.dialogRef.close();
   }
}
