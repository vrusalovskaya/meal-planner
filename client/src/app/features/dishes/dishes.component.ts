import { Component } from "@angular/core";
import { DishListComponent } from "./components/dish-list/dish-list.component";
import { DishesService } from "./services/dishes.service";
import { Dish } from "../../core/models/dish.model";
import { MatButton } from "@angular/material/button";
import { DialogService } from "../../core/services/dialog.service";
import { DishDialogService } from "./services/dish-dialog.service";
import { MealType } from "../../core/models/meal-type.model";

@Component({
   selector: "mp-dishes",
   imports: [DishListComponent, MatButton],
   templateUrl: "./dishes.component.html",
   styleUrl: "./dishes.component.scss",
})
export class DishesComponent {

   constructor(
      private readonly dishesService: DishesService,
      private readonly dialogService: DialogService,
      private readonly dishDialogService: DishDialogService,
   ) { }

   get dishes(): Dish[] { return this.dishesService.dishes }

   onDishDelete(dish: Dish) {
      this.dialogService.openConfirmationDialog({
         cancelButtonText: "Cancel",
         confirmButtonText: "Yes, delete",
         content: "Are you sure you want to delete this dish?",
         title: "Delete Dish",
         confirmAction: () => this.dishesService.deleteDish(dish.id)
      });
   }

   onAddDishClicked() {
      this.dishDialogService.openDishModificationDialog();
   }

   onDishEdit(dish: Dish) {
      this.dishDialogService.openDishModificationDialog(dish);
   }
}
