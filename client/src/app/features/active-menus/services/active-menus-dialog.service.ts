import { Injectable, Injector } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { DailyRation } from "../../../core/models/daily-ration.model";
import { RationReadingDialogComponentComponent } from "../components/ration-reading-dialog-component/ration-reading-dialog-component.component";
import { Menu } from "../../../core/models/menu.model";
import { CurrentMenuModificationDialogComponent } from "../components/current-menu-modification-dialog/current-menu-modification-dialog.component";
import { MenuModificationDialogComponent } from "../components/menu-modification-dialog/menu-modification-dialog.component";


@Injectable()
export class ActiveMenusDialogServiceService {

   constructor(
      private readonly dialog: MatDialog,
      private readonly injector: Injector,
   ) { }

   openRationReadingDialog(ration: DailyRation): void {
      this.dialog.open<RationReadingDialogComponentComponent, DailyRation, number>(RationReadingDialogComponentComponent, {
         width: "100rem",
         injector: this.injector,
         data: ration,
      });
   }

   openCurrentMenuModificationDialog(menu: Menu): void {
      this.dialog.open<CurrentMenuModificationDialogComponent, Menu, number>(CurrentMenuModificationDialogComponent, {
         width: "100rem",
         injector: this.injector,
         data: menu,
      });
   }

   openMenuModificationDialog(menu?: Menu): void {
      this.dialog.open<MenuModificationDialogComponent, Menu | undefined, number>(MenuModificationDialogComponent, {
         width: "100rem",
         injector: this.injector,
         data: menu,
      },);
   }

}
