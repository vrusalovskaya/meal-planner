import { Component } from "@angular/core";

import { MenusListComponent } from "./menus-list/menus-list.component";
import { ArchiveMenusService } from "./services/archive-menus.service";
import { Menu } from "../../core/models/menu.model";

@Component({
   selector: "mp-menus",
   imports: [MenusListComponent],
   templateUrl: "./archive-menus.component.html",
   styleUrl: "./archive-menus.component.scss"
})
export class ArchiveMenusComponent {
   constructor(
      private readonly menusService: ArchiveMenusService
   ) { }

   get pastMenus(): Menu[] {return this.menusService.pastMenus}

}
