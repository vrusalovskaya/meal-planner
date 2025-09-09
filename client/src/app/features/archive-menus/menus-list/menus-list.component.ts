import { ChangeDetectionStrategy, Component, Input } from "@angular/core";
import { Menu } from "../../../core/models/menu.model";
import { MenuComponent } from "../menu/menu.component";

@Component({
  selector: "mp-menus-list",
  imports: [MenuComponent],
  templateUrl: "./menus-list.component.html",
  styleUrl: "./menus-list.component.scss"
})
export class MenusListComponent { 
   @Input ({ required: true }) menus: Menu[] = [];


}
