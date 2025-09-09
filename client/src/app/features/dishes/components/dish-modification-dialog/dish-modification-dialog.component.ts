import { Component, Inject } from "@angular/core";
import { Dish } from "../../../../core/models/dish.model";
import { DishesService } from "../../services/dishes.service";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from "@angular/material/dialog";
import { MatButtonModule } from "@angular/material/button";
import { FormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatChipSelectionChange, MatChipsModule } from "@angular/material/chips";
import { MEAL_TYPES_KEYS, MealType } from "../../../../core/models/meal-type.model";
import { DishIngredientsModificationComponent } from "../dish-ingredients-modification/dish-ingredients-modification.component";
import { DishIngredient } from "../../../../core/models/dish-ingredient.model";

interface LocalMealOption {
   selected: boolean;
   value: keyof typeof MealType;
}

@Component({
   selector: "mp-dish-modification-dialog",
   imports: [
      MatDialogActions,
      MatDialogClose,
      MatDialogContent,
      MatDialogTitle,
      MatButtonModule,
      MatDialogModule,
      FormsModule,
      MatFormFieldModule,
      MatInputModule,
      MatChipsModule,
      DishIngredientsModificationComponent,

   ],
   templateUrl: "./dish-modification-dialog.component.html",
   styleUrl: "./dish-modification-dialog.component.scss",
})
export class DishModificationDialogComponent {

   readonly title: string;
   readonly isEdit: boolean = false;
   readonly mealTypesOptions: LocalMealOption[];

   get selectedMealTypeOptions(): string[] {
      return this.mealTypesOptions.filter(x => x.selected).map(x => x.value);
   }

   constructor(
      private readonly dishesService: DishesService,
      private dialogRef: MatDialogRef<DishModificationDialogComponent>,
      @Inject(MAT_DIALOG_DATA) readonly dish: Dish
   ) {
      if (this.dish) {
         this.isEdit = true;
         this.title = "Edit dish";
         this.mealTypesOptions = MEAL_TYPES_KEYS.map(k => ({
            selected: !!this.dish!.mealTypes.find(t => t === k),
            value: k
         }));
         this.dish = {...dish}
      } else {
         this.mealTypesOptions = MEAL_TYPES_KEYS.map(k => ({ selected: false, value: k }));
         this.title = "Add dish";
         this.dish = {
            id: 0,
            name: "",
            dishIngredients: [],
            mealTypes: [],
            recipeDescription: "",
            recipeExternalLink: ""
         };
      }
   }

   save() {
      this.dish.mealTypes = this.selectedMealTypeOptions as MealType[];
      if (this.isEdit) {
         this.edit();
      } else {
         this.add();
      }
   }

   async add() {
      await this.dishesService.addDish(this.dish);
      this.dialogRef.close();
   }

   async edit() {
      await this.dishesService.editDish(this.dish);
      this.dialogRef.close();
   }

   onMealTypeChange(option: LocalMealOption, $event: MatChipSelectionChange) {
      option.selected = $event.selected;
   }

   onIngredientsUpdate(latest: DishIngredient[]) {
      this.dish.dishIngredients = latest;
   }
}
