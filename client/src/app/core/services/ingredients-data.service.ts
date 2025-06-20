import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { firstValueFrom } from "rxjs";

import { Ingredient } from "../models/ingredient.model";
import { environment } from "../../../environments/environment";

@Injectable({
   providedIn: "root"
})
export class IngredientsDataService {

   constructor(private readonly http: HttpClient) { }

   async getIngredients(): Promise<Ingredient[]> {
      return await firstValueFrom(this.http.get<Ingredient[]>(this.buildUrl("ingredients")))
   }

    async deleteIngredient(id: Ingredient["id"]): Promise<void> {
      return await firstValueFrom(this.http.delete<void>(this.buildUrl(`ingredients/${id.toString()}`)))
   }

   private buildUrl(value: string): string {
      return `${environment.apiUrl}/${value}`;
   }
}
