import { Component, Inject } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatChipsModule } from "@angular/material/chips";
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatNativeDateModule, provideNativeDateAdapter } from "@angular/material/core";

import { CommonModule } from "@angular/common";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { DailyRation } from "../../../../core/models/daily-ration.model";
import { Dish } from "../../../../core/models/dish.model";
import { DishEditingComponent } from "../dish-editing/dish-editing.component";
import { Menu } from "../../../../core/models/menu.model";
import { RationItem } from "../../../../core/models/ration-item.model";
import { MEAL_TYPES_KEYS, MealType } from "../../../../core/models/meal-type.model";
import { ActiveMenusService } from "../../services/active-menus.service";
import { DialogService } from "../../../../core/services/dialog.service";


const EMPTY_DISH: Dish = {
   id: -1,
   dishIngredients: [],
   mealTypes: [],
   name: "",
   recipeDescription: "",
   recipeExternalLink: ""
};


@Component({
   selector: "mp-menu-modification-dialog",
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
      MatDatepickerModule,
      MatAutocompleteModule,
      CommonModule,
      DishEditingComponent,
      MatNativeDateModule
   ],
   providers: [provideNativeDateAdapter()],
   templateUrl: "./menu-modification-dialog.component.html",
   styleUrl: "./menu-modification-dialog.component.scss"
})
export class MenuModificationDialogComponent {

   readonly title: string;
   readonly isEdit: boolean = false;
   dishes: Dish[];

   constructor(
      private readonly currentMenuService: ActiveMenusService,
      private dialogRef: MatDialogRef<MenuModificationDialogComponent>,
      private readonly dialogService: DialogService,
      @Inject(MAT_DIALOG_DATA) public menu: Menu
   ) {
      if (this.menu && this.menu.id) {
         this.isEdit = true;
         this.title = 'Edit menu';
         this.menu = { ...menu };
      } else {
         this.title = 'Add menu';
         this.menu = {
            id: 0,
            startDate: null as any,
            endDate: null as any,
            rations: []
         };
      }
      this.dishes = this.currentMenuService.dishes;
   }

   dateFilter = (date: Date | null) => {
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      return date !== null && date >= today;
   };

   onDatePickerClosed() {
      if (this.validateDates()) {
         this.generateRations(new Date(this.menu.startDate), new Date(this.menu.endDate))
      } else {
         this.menu.rations = []
      }
   }

   validateDates(): boolean {
      if (!this.menu.startDate || !this.menu.endDate) {
         return false;
      }

      const start = new Date(this.menu.startDate);
      const end = new Date(this.menu.endDate);
      const today = new Date();
      today.setHours(0, 0, 0, 0);

      if (start < today || end < today) {
         return false;
      }
      if (end < start) {
         return false;
      }
      return true;
   }

   private generateRations(start: Date, end: Date): void {
      const rations: DailyRation[] = [];
      let current = new Date(start);

      let rationId = 1;
      while (this.isOnOrBefore(current, end)) {
         const rationItems: RationItem[] = MEAL_TYPES_KEYS.map((mealType, idx) => ({
            id: idx + 1,
            mealType,
            dish: { ...EMPTY_DISH }
         }));

         rations.push({
            id: rationId++,
            date: this.formatDate(current),
            rationItems
         });

         current.setDate(current.getDate() + 1);
      }

      this.menu.rations = rations;
   }

   private isOnOrBefore(dateA: Date, dateB: Date) {
      const normalizedA = new Date(dateA.getFullYear(), dateA.getMonth(), dateA.getDate());
      const normalizedB = new Date(dateB.getFullYear(), dateB.getMonth(), dateB.getDate());

      return normalizedA.getTime() <= normalizedB.getTime();
   }

   dishesByMealType(mealType: MealType) {
      return this.currentMenuService.dishes.filter(i => i.mealTypes.includes(mealType));
   }

   isMenuValid(): boolean {
      if (!this.menu.startDate || !this.menu.endDate) {
         return false;
      }

      for (const ration of this.menu.rations) {
         for (const item of ration.rationItems) {
            if (!item.dish || (item.dish.id === -1)) {
               return false;
            }
         }
      }

      return true;
   }

   save() {
      this.menu.startDate = this.formatDate(new Date(this.menu.startDate));
      this.menu.endDate = this.formatDate(new Date(this.menu.endDate));
      this.menu.rations = this.menu.rations.map(r => ({
         ...r,
         date: this.formatDate(new Date(r.date))
      }))
      if (this.isEdit) {
         this.edit();
      } else {
         this.add();
      }
   }

   async add() {
      try {
         await this.currentMenuService.addMenu(this.menu);
         this.dialogRef.close();
      } catch (error: any) {
         this.dialogService.openConfirmationDialog({
            confirmButtonText: "Ok",
            content: error.error,
            title: "Error occured while creating menu",
         });
      }
   }

   async edit() {
      try {
         await this.currentMenuService.editMenu(this.menu);
         this.dialogRef.close();
      } catch (error: any) {
         this.dialogService.openConfirmationDialog({
            confirmButtonText: "Ok",
            content: error.error,
            title: "Error occured while editing menu",
         });
      }
   }

   formatDate(date: Date): string {
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      return `${year}-${month}-${day}`;
   }

}
