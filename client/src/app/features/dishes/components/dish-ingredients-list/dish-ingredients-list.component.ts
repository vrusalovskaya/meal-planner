import { ChangeDetectionStrategy, Component, Input, OnInit } from "@angular/core";
import { DishIngredient } from "../../../../core/models/dish-ingredient.model";
import { MatTableModule } from "@angular/material/table";

@Component({
  selector: "mp-dish-ingredients-list",
  imports: [MatTableModule],
  templateUrl: "./dish-ingredients-list.component.html",
  styleUrl: "./dish-ingredients-list.component.scss"
})
export class DishIngredientsListComponent implements OnInit {
   ngOnInit(): void {
      console.log(this.dishIngredients)
   }
    @Input({ required: true }) dishIngredients: DishIngredient[] = []

     displayedColumns: string[] = ["ingredient", "quantity"];


 }
