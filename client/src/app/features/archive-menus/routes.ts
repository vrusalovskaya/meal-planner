import { ResolveFn, Route } from "@angular/router";
import { inject } from "@angular/core";

import { ArchiveMenusComponent } from "./archive-menus.component";
import { ArchiveMenusService } from "./services/archive-menus.service";

const dataResolver: ResolveFn<unknown> = () => {

    const archiveMenusService = inject(ArchiveMenusService);

    return archiveMenusService.refresh();
};

export const ROUTES: Route[] = [{
    path: "",
    pathMatch: "prefix",
    component: ArchiveMenusComponent,
    providers: [
       ArchiveMenusService,
    ],
    resolve: { _: dataResolver }
}];