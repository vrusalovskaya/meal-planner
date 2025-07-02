import { ResolveFn, Route } from "@angular/router";
import { inject } from "@angular/core";

import { IngredientsComponent } from "./ingredients.component";
import { IngredientsService } from "./services/ingredients.service";
import { IngredientDialogService } from "./services/ingredient-dialog.service";

const dataResolver: ResolveFn<unknown> = () => {

    const ingredientsService = inject(IngredientsService);

    return ingredientsService.refresh();
};

export const ROUTES: Route[] = [{
    path: "",
    pathMatch: "prefix",
    component: IngredientsComponent,
    providers: [
       IngredientsService,
       IngredientDialogService
    ],
    resolve: { _: dataResolver }
}];