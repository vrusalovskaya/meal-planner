import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Menu } from "../../../../core/models/menu.model";
import { MatButtonModule } from "@angular/material/button";
import { MatExpansionModule } from "@angular/material/expansion";
import { CommonModule } from "@angular/common";
import { MatChipsModule } from "@angular/material/chips";
import { MatCardModule } from "@angular/material/card";
import { DailyRation } from "../../../../core/models/daily-ration.model";
import { ActiveMenusDialogServiceService } from "../../services/active-menus-dialog.service";

@Component({
   selector: "mp-future-menu",
   imports: [
      MatExpansionModule,
      MatButtonModule,
      MatCardModule, 
      MatChipsModule,
      CommonModule
   ],
   templateUrl: "./future-menu.component.html",
   styleUrl: "./future-menu.component.scss"
})
export class FutureMenuComponent {

   @Input({ required: true }) futureMenu!: Menu;
   @Output() deleteAction = new EventEmitter();
   @Output() editAction = new EventEmitter();

   constructor(
         private readonly currentMenuDialogService: ActiveMenusDialogServiceService,
      ) { }
   

   onDeleteButtonClicked(event: MouseEvent) {
      event.stopPropagation();
      this.deleteAction.emit();
   }

   onEditButtonClicked(event: MouseEvent) {
      event.stopPropagation();
      this.editAction.emit();
   }

   onRationCardClicked(ration: DailyRation) {
         this.currentMenuDialogService.openRationReadingDialog(ration);
      }
}
