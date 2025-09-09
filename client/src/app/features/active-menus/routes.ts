import { inject } from "@angular/core";
import { ResolveFn, Route } from "@angular/router";

import { ActiveMenusService } from "./services/active-menus.service";
import { ActiveMenusComponent } from "./active-menus.component";
import { ActiveMenusDialogServiceService } from "./services/active-menus-dialog.service";

const dataResolver: ResolveFn<unknown> = () => {
   const activeMenusService = inject(ActiveMenusService);

   return activeMenusService.refresh();
};

export const ROUTES: Route[] = [{
   path: "",
   pathMatch: "prefix",
   component: ActiveMenusComponent,
   providers: [
      ActiveMenusService,
      ActiveMenusDialogServiceService
   ],
   resolve: { _: dataResolver }
}];