import { Injectable } from "@angular/core";
import { MenusDataService } from "../../../core/services/menus-data.service";
import { Menu } from "../../../core/models/menu.model";
import { DishesDataService } from "../../../core/services/dishes-data.service";
import { Dish } from "../../../core/models/dish.model";

@Injectable()
export class ActiveMenusService {

   constructor(
      private readonly menusDataService: MenusDataService,
      private readonly dishesDataService: DishesDataService
   ) { }

   currentMenu: Menu | null = null;
   futureMenus: Menu[] = [];
   dishes: Dish[] = [];

   async refresh(): Promise<void> {
      const [currentMenu, futureMenus, dishes] = await Promise.all([
         this.menusDataService.getCurrentMenu(),
         this.menusDataService.getFutureMenus(),
         this.dishesDataService.getDishes()
      ]);
      this.currentMenu = currentMenu;
      this.futureMenus = futureMenus;
      this.dishes = dishes;
   }

   async editCurrentMenu(menu: Menu): Promise<void> {
      let editedMenu: Menu = await this.menusDataService.editCurrentMenu(menu);
      this.currentMenu = editedMenu;
   }

   async addMenu(menu: Menu): Promise<void> {
      await this.menusDataService.addMenu(menu);
      await this.refresh();
   }

   async editMenu(menu: Menu): Promise<void> {
      await this.menusDataService.editMenu(menu);
      await this.refresh();
   }

   async deleteMenu(id: Menu["id"]): Promise<void> {
      await this.menusDataService.deleteMenu(id)
      this.futureMenus = this.futureMenus.filter(i => i.id !== id);
   }
}
