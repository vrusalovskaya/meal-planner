import { Injectable, Injector } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";

import { firstValueFrom, map } from "rxjs";

import { ConfirmationDialogComponent } from "../components/confirmation-dialog/confirmation-dialog.component";
import { ConfirmationDialogParameters } from "../models/confirmation-dialog-parameters.model";

@Injectable({
   providedIn: "root"
})
export class DialogService {

   constructor(
      private readonly dialog: MatDialog,
      private readonly injector: Injector
   ) { }

   openConfirmationDialog(paratemers: ConfirmationDialogParameters): Promise<boolean> {
      const dialogRef = this.dialog.open<ConfirmationDialogComponent, ConfirmationDialogParameters, boolean>(ConfirmationDialogComponent, {
         minWidth: "300px",
         data: paratemers,
         injector: this.injector,
         disableClose: true,
      });

      return firstValueFrom(dialogRef.afterClosed().pipe(map(result => !!result)));
   }
}
