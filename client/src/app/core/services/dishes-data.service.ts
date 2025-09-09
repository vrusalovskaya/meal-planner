import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Dish } from "../models/dish.model";
import { environment } from "../../../environments/environment";

@Injectable({
   providedIn: "root"
})
export class DishesDataService {

   constructor(private readonly http: HttpClient) { }

   async getDishes(): Promise<Dish[]> {
      return await firstValueFrom(this.http.get<Dish[]>(this.buildUrl("dishes")))
   }

   async deleteDish(id: Dish["id"]): Promise<void> {
      return await firstValueFrom(this.http.delete<void>(this.buildUrl(`dishes/${id.toString()}`)))
   }

   async addDish(dish: Dish): Promise<Dish> {
      return await firstValueFrom(this.http.post<Dish>(this.buildUrl('dishes'), dish));
   }

   async editDish(dish: Dish): Promise<Dish> {
      return await firstValueFrom(this.http.patch<Dish>(this.buildUrl(`dishes/${dish.id.toString()}`), dish));
   }

   private buildUrl(value: string): string {
      return `${environment.apiUrl}/${value}`;
   }
}
