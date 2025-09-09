import { Component } from "@angular/core";
import { ActiveMenusService } from "./services/active-menus.service";
import { Menu } from "../../core/models/menu.model";
import { CommonModule } from "@angular/common";
import { MatCardModule } from "@angular/material/card";
import { MatChipsModule } from "@angular/material/chips";
import { ActiveMenusDialogServiceService } from "./services/active-menus-dialog.service";
import { DailyRation } from "../../core/models/daily-ration.model";
import { MatButton, MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { FutureMenusListComponent } from "./components/future-menus-list/future-menus-list.component";
import { DialogService } from "../../core/services/dialog.service";

@Component({
   selector: "mp-active-menus",
   imports: [CommonModule, MatCardModule, MatChipsModule, MatButton, MatIconModule, MatButtonModule, FutureMenusListComponent],
   templateUrl: "./active-menus.component.html",
   styleUrl: "./active-menus.component.scss"
})
export class ActiveMenusComponent {
   constructor(
      private readonly activeMenusService: ActiveMenusService,
      private readonly activeMenusDialogService: ActiveMenusDialogServiceService,
       private readonly dialogService: DialogService,
   ) { }

   get currentMenu(): Menu | null { return this.activeMenusService.currentMenu }
   get futureMenus(): Menu[] { return this.activeMenusService.futureMenus }

   onRationCardClicked(ration: DailyRation) {
      this.activeMenusDialogService.openRationReadingDialog(ration);
   }

   onEditCurrentMenuClicked(currentMenu: Menu) {
      let menu = structuredClone(currentMenu);
      this.activeMenusDialogService.openCurrentMenuModificationDialog(menu)
   }

   onAddMenuClicked() {
      this.activeMenusDialogService.openMenuModificationDialog();
   }

   onMenuDelete(menu: Menu) {
      this.dialogService.openConfirmationDialog({
         cancelButtonText: "Cancel",
         confirmButtonText: "Yes, delete",
         content: "Are you sure you want to delete this menu?",
         title: "Delete Menu",
         confirmAction: () => this.activeMenusService.deleteMenu(menu.id)
      });
   }

   onMenuEdit(menu: Menu) {
      this.activeMenusDialogService.openMenuModificationDialog(menu);
   }
}
