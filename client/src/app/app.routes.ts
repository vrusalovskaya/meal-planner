import { Routes } from "@angular/router";
import { RoutePaths } from "./route-paths";

import { NotFoundComponent } from "./core/components/not-found/not-found.component";

export const routes: Routes = [
    {
        path: RoutePaths.Ingredients,
        title: "Meal Planner - Ingredients",
        loadChildren: () => import("./features/ingredients/routes").then(m => m.ROUTES),
    },
    {
        path: RoutePaths.Dishes,
        title: "Meal Planner - Dishes",
        loadChildren: () => import("./features/dishes/routes").then(m => m.ROUTES),
    },
    { path: RoutePaths.Home, redirectTo: RoutePaths.Dishes, pathMatch: "full" },
    { path: "**", component: NotFoundComponent },
];