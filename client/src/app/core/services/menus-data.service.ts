import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { firstValueFrom } from "rxjs";

import { Menu } from "../models/menu.model";
import { environment } from "../../../environments/environment";

@Injectable({
   providedIn: "root"
})
export class MenusDataService {

   constructor(private readonly http: HttpClient) { }

   async getMenus(): Promise<Menu[]> {
      return await firstValueFrom(this.http.get<Menu[]>(this.buildUrl("menus")))
   }

   async getPastMenus(): Promise<Menu[]> {
      return await firstValueFrom(this.http.get<Menu[]>(this.buildUrl("menus/past")))
   }

   async getFutureMenus(): Promise<Menu[]> {
      return await firstValueFrom(this.http.get<Menu[]>(this.buildUrl("menus/future")))
   }

   async getCurrentMenu(): Promise<Menu> {
      return await firstValueFrom(this.http.get<Menu>(this.buildUrl("menus/current")))
   }

   async addMenu(menu: Menu): Promise<Menu> {
      return await firstValueFrom(this.http.post<Menu>(this.buildUrl("menus"), menu))
   }

   async deleteMenu(id: Menu["id"]): Promise<void> {
      return await firstValueFrom(this.http.delete<void>(this.buildUrl(`menus/${id.toString()}`)))
   }

   async editMenu(menu: Menu): Promise<Menu> {
      console.log(menu);
      return await firstValueFrom(this.http.patch<Menu>(this.buildUrl(`menus/${menu.id.toString()}`), menu))
   }

   async editCurrentMenu(menu: Menu): Promise<Menu> {
      return await firstValueFrom(this.http.patch<Menu>(this.buildUrl(`menus/current/${menu.id.toString()}`),
         this.parseToUpdatedDishesList(menu)))
   }

   private buildUrl(value: string): string {
      return `${environment.apiUrl}/${value}`;
   }

   parseToUpdatedDishesList(menu: Menu): { rationItemId: number; dishId: number }[] {
      const updatedDishes: { rationItemId: number; dishId: number }[] = [];

      for (const ration of menu.rations) {
         for (const rationItem of ration.rationItems) {
            updatedDishes.push({
               rationItemId: rationItem.id,
               dishId: rationItem.dish.id
            });
         }
      }

      return updatedDishes;
   }
}
