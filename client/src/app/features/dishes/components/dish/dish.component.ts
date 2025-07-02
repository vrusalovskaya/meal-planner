import { Component, EventEmitter, Input, OnInit, Output, SecurityContext } from "@angular/core";
import { Dish } from "../../../../core/models/dish.model";
import { MatButtonModule } from "@angular/material/button";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatChipsModule } from "@angular/material/chips";
import { DishIngredientsListComponent } from "../dish-ingredients-list/dish-ingredients-list.component";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";

@Component({
   selector: "mp-dish",
   imports: [
      DishIngredientsListComponent,
      MatExpansionModule,
      MatButtonModule,
      MatChipsModule
   ],
   templateUrl: "./dish.component.html",
   styleUrl: "./dish.component.scss",
})
export class DishComponent implements OnInit {

   @Input({ required: true }) dish!: Dish;
   @Output() deleteAction = new EventEmitter();
   @Output() editAction = new EventEmitter();

   trustedUrl: SafeUrl | null = null;

   constructor(private readonly sanitizer: DomSanitizer) { }

   ngOnInit() {
      if (this.dish.recipeExternalLink && this.isValidUrl(this.dish.recipeExternalLink)) {
         this.trustedUrl = this.sanitizer.bypassSecurityTrustUrl(this.dish.recipeExternalLink);
      }
   }

   onDeleteButtonClicked(event: MouseEvent) {
      event.stopPropagation();
      this.deleteAction.emit();
   }
   onEditButtonClicked(event: MouseEvent) {
      event.stopPropagation();
      this.editAction.emit();
   }

   private isValidUrl(url: string): boolean {
      try {
         const parsed = new URL(url);
         return parsed.protocol === 'http:' || parsed.protocol === 'https:';
      } catch (_) {
         return false;
      }
   }
}
