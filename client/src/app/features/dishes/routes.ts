import { ResolveFn, Route } from "@angular/router";
import { inject } from "@angular/core";
import { DishesComponent } from "./dishes.component";
import { DishesService } from "./services/dishes.service";
import { DishDialogService } from "./services/dish-dialog.service";

const dataResolver: ResolveFn<unknown> = () => {
   const dishesService = inject(DishesService);

   return dishesService.refresh();
};

export const ROUTES: Route[] = [{
   path: "",
   pathMatch: "prefix",
   component: DishesComponent,
   providers: [
      DishesService,
      DishDialogService,
   ],
   resolve: { _: dataResolver }
}];