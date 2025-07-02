import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Dish } from "../../../../core/models/dish.model";
import { DishComponent } from "../dish/dish.component";
import { MatAccordion, MatExpansionModule } from "@angular/material/expansion";

@Component({
   selector: "mp-dish-list",
   imports: [DishComponent, MatExpansionModule],
   templateUrl: "./dish-list.component.html",
   styleUrl: "./dish-list.component.scss"
})
export class DishListComponent {

   @Input({ required: true }) dishes: Dish[] = []
   @Output() dishDelete = new EventEmitter<Dish>();
   @Output() dishEdit = new EventEmitter<Dish>();

   onDishDelete(dish: Dish) {
      this.dishDelete.emit(dish);
   }

   onDishEdit(dish: Dish) {
      this.dishEdit.emit(dish);
   }
}
