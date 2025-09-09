import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Menu } from "../../../../core/models/menu.model";
import { MatExpansionModule } from "@angular/material/expansion";
import { FutureMenuComponent } from "../future-menu/future-menu.component";

@Component({
   selector: "mp-future-menus-list",
   imports: [MatExpansionModule, FutureMenuComponent],
   templateUrl: "./future-menus-list.component.html",
   styleUrl: "./future-menus-list.component.scss"
})
export class FutureMenusListComponent {

   @Input({ required: true }) futureMenus: Menu[] = [];
   @Output() menuDelete = new EventEmitter<Menu>();
   @Output() menuEdit = new EventEmitter<Menu>();

   get sortedMenus(): Menu[] {
    return [...this.futureMenus].sort(
      (a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime()
    );
  }

   onMenuDelete(menu: Menu) {
      this.menuDelete.emit(menu);
   }

   onMenuEdit(menu: Menu) {
      this.menuEdit.emit(menu);
   }
}
