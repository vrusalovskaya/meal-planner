import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { MatButton } from "@angular/material/button";

import { Ingredient } from "../../../../core/models/ingredient.model";
import { DialogService } from "../../../../core/services/dialog.service";

@Component({
   selector: "mp-ingredient",
   imports: [MatCardModule, MatButton],
   templateUrl: "./ingredient.component.html",
   styleUrl: "./ingredient.component.scss",
   changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IngredientComponent {
   @Input({ required: true }) ingredient!: Ingredient;
   @Output() deleteAction = new EventEmitter();
   @Output() editAction = new EventEmitter();

   constructor(
      private readonly dialogService: DialogService
   ) { }

   onDeleteButtonClicked() {
      this.deleteAction.emit()
   }

   onEditButtonClicked(){
      this.editAction.emit();
   }
}
