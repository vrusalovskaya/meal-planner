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
    {
        path: RoutePaths.ArchiveMenus,
        title: "Meal Planner - Archive Menus",
        loadChildren: () => import("./features/archive-menus/routes").then(m => m.ROUTES),
    },
    {
        path: RoutePaths.ActiveMenus,
        title: "Meal Planner - Active Menus",
        loadChildren: () => import("./features/active-menus/routes").then(m => m.ROUTES),
    },
    { path: RoutePaths.Home, redirectTo: RoutePaths.ActiveMenus, pathMatch: "full" },
    { path: "**", component: NotFoundComponent },
];