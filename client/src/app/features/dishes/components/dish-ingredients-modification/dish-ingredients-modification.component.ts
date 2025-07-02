import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from "@angular/material/autocomplete";
import { MatInputModule } from "@angular/material/input";
import { Ingredient } from "../../../../core/models/ingredient.model";
import { FormsModule, NgModel } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { CommonModule } from "@angular/common";
import { DishesService } from "../../services/dishes.service";
import { DishIngredient } from "../../../../core/models/dish-ingredient.model";
import { MatSelectModule } from "@angular/material/select";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";

const EMPTY_DISH_INGREDIENT: DishIngredient = {
   id: null,
   ingredient: {
      id: -1,
      name: "",
      possibleUnits: [],
   },
   quantity: 0,
   unit: "",
   comment: "",
};

@Component({
   selector: "mp-dish-ingredients-modification",
   imports: [
      CommonModule,
      FormsModule,
      MatFormFieldModule,
      MatAutocompleteModule,
      MatInputModule,
      MatSelectModule,
      MatButtonModule,
      MatIconModule
   ],
   templateUrl: "./dish-ingredients-modification.component.html",
   styleUrl: "./dish-ingredients-modification.component.scss",
})
export class DishIngredientsModificationComponent implements OnInit {

   filteredIngredients: Ingredient[] = [];
   dishIngredients: DishIngredient[] = [];

   @Input() initialIngredients: DishIngredient[] = [];
   @Output() ingredientsChange = new EventEmitter<DishIngredient[]>();

   ngOnInit() {
      if (this.initialIngredients && this.initialIngredients.length > 0) {
         this.dishIngredients = [...this.initialIngredients];
      } else {
         this.dishIngredients = [{ ...EMPTY_DISH_INGREDIENT }];
      }
      this.emitChange();
   }

   constructor(private readonly dishesService: DishesService) {
      this.filteredIngredients = this.dishesService.ingredients;
   }

   onIngredientInput(input: NgModel): void {
      let value;
      if (typeof input.value === "string" || input.value instanceof String) {
         input.control.setErrors({});
         value = input.value.toLowerCase();
      } else {
         value = input.value.name;
         if (value) {
            input.control.setErrors(null);
         }
      }

      this.filteredIngredients = this.dishesService.ingredients.filter(i => i.name.toLowerCase().includes(value));
   }

   onIngredientSelected($event: MatAutocompleteSelectedEvent, dishIngredient: DishIngredient, input: NgModel): void {
      input.control.setErrors(null);
      let ingredient = $event.option.value as Ingredient;
      dishIngredient.ingredient = ingredient;
      if (ingredient.possibleUnits.length > 0) {
         dishIngredient.unit = ingredient.possibleUnits[0];
      }
      this.emitChange();
   }

   formatOption(value: Ingredient | null): string {
      return value?.name || "";
   }

   addDishIngredient(): any {
      this.dishIngredients.push({ ...EMPTY_DISH_INGREDIENT });
      this.emitChange();
   }

   deleteDishIngredient(dishIngredient: DishIngredient): any {
      this.dishIngredients = this.dishIngredients.filter(i => i !== dishIngredient);
      this.emitChange();
   }

   public emitChange(): void {
      const validDishIngredients = this.dishIngredients.filter(i => i.ingredient.id !== -1);
      this.ingredientsChange.emit(validDishIngredients);
   }
}
