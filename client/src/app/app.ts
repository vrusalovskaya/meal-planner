import { Component } from "@angular/core";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router, RouterOutlet } from "@angular/router";

import { filter, map, debounceTime, distinctUntilChanged, tap } from "rxjs";

import { LoadingOverlayService } from "./core/loading-overlay/loading-overlay.service";
import { AppNavigationComponent } from "./core/components/app-navigation/app-navigation.component";

@Component({
   selector: "mp-root",
   imports: [RouterOutlet, AppNavigationComponent],
   templateUrl: "./app.html",
   styleUrl: "./app.scss"
})
export class App {

   constructor(
      private readonly router: Router,
      private readonly loadingOverlayService: LoadingOverlayService,
   ) {
      this.router.events
         .pipe(
            takeUntilDestroyed(),
            filter(
               event => event instanceof NavigationStart
                  || event instanceof NavigationEnd
                  || event instanceof NavigationCancel
                  || event instanceof NavigationError
            ),
            map(event => event instanceof NavigationStart),
            debounceTime(100),
            distinctUntilChanged(),
            tap(shouldShowLoading => {
               if (shouldShowLoading) {
                  this.loadingOverlayService.show();
               } else {
                  this.loadingOverlayService.hide();
               }
            }),
         )
         .subscribe();
   }
}
