import { Component, Inject } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButton, MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { Ingredient } from "../../../../core/models/ingredient.model";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from "@angular/material/dialog";
import { IngredientsService } from "../../services/ingredients.service";
import { MatCheckboxChange, MatCheckboxModule } from "@angular/material/checkbox";

interface LocalUnit {
   selected: boolean;
   value: string;
}

@Component({
   selector: "mp-modification-dialog",
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
      MatCheckboxModule
   ],
   templateUrl: "./ingredient-modification-dialog.component.html",
   styleUrl: "./ingredient-modification-dialog.component.scss"
})
export class IngredientModificationDialogComponent {

   readonly ingredient: Ingredient;
   readonly title: string;
   readonly isEdit: boolean = false;
   readonly units: LocalUnit[];

   get selectedUnits(): string[] {
      return this.units.filter(x => x.selected).map(x => x.value);
   }

   constructor(
      private readonly ingredientService: IngredientsService,
      private dialogRef: MatDialogRef<IngredientModificationDialogComponent>,
      @Inject(MAT_DIALOG_DATA) data?: Ingredient
   ) {
      this.units = this.ingredientService.units.map(x => ({ selected: false, value: x }));
      if (data) {
         this.isEdit = true;
         data.possibleUnits.forEach(u => {
            const unit = this.units.find(x => x.value === u);
            if (unit) {
               unit.selected = true;
            }
         });
      }
      this.ingredient = data ? { ...data } : { id: 0, name: "", possibleUnits: [] };
      this.title = data ? "Edit ingredient" : "Add ingredient";
   }

   onUnitSelectionChange(unit: LocalUnit, $event: MatCheckboxChange) {
      unit.selected = $event.checked;
   }

   save() {
      this.ingredient.possibleUnits = this.selectedUnits;
      if (this.isEdit) {
         this.edit()
      } else {
         this.add()
      }
   }

   async edit() {
      await this.ingredientService.editIngredient(this.ingredient);
      this.dialogRef.close();
   }

   async add() {
      await this.ingredientService.addIngredient(this.ingredient);
      this.dialogRef.close();
   }

   cancel() {
      this.dialogRef.close();
   }
}
