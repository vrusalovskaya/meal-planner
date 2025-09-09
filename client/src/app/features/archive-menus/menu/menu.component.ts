import { ChangeDetectionStrategy, Component, Input } from "@angular/core";
import { Menu } from "../../../core/models/menu.model";
import { MatCardModule } from "@angular/material/card";
import { CommonModule } from "@angular/common";
import { MatChipsModule } from "@angular/material/chips";

@Component({
  selector: "mp-menu",
  imports: [MatCardModule, CommonModule, MatChipsModule],
  templateUrl: "./menu.component.html",
  styleUrl: "./menu.component.scss"
})
export class MenuComponent { 
   @Input({ required: true }) menu!: Menu;
}
