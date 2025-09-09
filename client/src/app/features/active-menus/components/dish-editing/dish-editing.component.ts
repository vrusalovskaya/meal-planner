import { Component, EventEmitter, Input, Output, ViewChild } from "@angular/core";
import { ControlContainer, FormsModule, NgForm, NgModel } from "@angular/forms";
import { MatAutocompleteModule, MatAutocompleteSelectedEvent, MatAutocompleteTrigger } from "@angular/material/autocomplete";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { Dish } from "../../../../core/models/dish.model";

@Component({
   selector: "mp-dish-editing",
   imports: [
      FormsModule,
      MatFormFieldModule,
      MatInputModule,
      MatAutocompleteModule
   ],
   templateUrl: "./dish-editing.component.html",
   styleUrl: "./dish-editing.component.scss"
})
export class DishEditingComponent {
   private dishesListInternal: Dish[] = [];

   @ViewChild(MatAutocompleteTrigger)
   autocompleteTrigger!: MatAutocompleteTrigger;

   @Input()
   get dishesList(): Dish[] {
      return this.dishesListInternal;
   };
   set dishesList(value: Dish[]) {
      if (this.dishesListInternal !== value) {
         this.dishesListInternal = value;
         this.filteredDishes = value;
         if (this.autocompleteTrigger) {
            this.autocompleteTrigger.autocomplete.options.forEach(x => x.deselect())
         }
      }
   }

   @Input({ required: true }) initialDish!: Dish;
   @Output() dishChange = new EventEmitter<Dish>();
   @Output() selectedDish: Dish = this.initialDish;

   filteredDishes: Dish[] = [];

   onDishInput(input: NgModel): void {
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

      this.filteredDishes = this.dishesList.filter(i => i.name.toLowerCase().includes(value));
   }

   onDishSelected($event: MatAutocompleteSelectedEvent, input: NgModel): void {
      input.control.setErrors(null);
      let dish = $event.option.value as Dish;
      this.initialDish = dish;
      this.dishChange.emit(this.initialDish);
   }

   formatOption(value: Dish | null): string {
      return value?.name || "";
   }
}
