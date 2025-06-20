import { Injectable, Injector, inject } from "@angular/core";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";

import { LoadingOverlayComponent } from "./loading-overlay.component";

@Injectable({
   providedIn: "root"
})
export class LoadingOverlayService {

   private readonly dialog = inject(MatDialog);
   private readonly injector = inject(Injector);

   private count = 0;
   private dialogRef?: MatDialogRef<LoadingOverlayComponent>;

   show(): void {
      this.count++;

      if (this.dialogRef) {
         return;
      }

      this.dialogRef = this.dialog.open(LoadingOverlayComponent, {
         injector: this.injector,
         disableClose: true,
         hasBackdrop: true,
         panelClass: "mp-loading-overlay-panel",
      });
   }

   hide(): void {
      if (this.count > 0) {
         this.count--;
      }

      if (this.dialogRef && this.count === 0) {
         this.dialogRef.close();
         this.dialogRef = undefined;
      }
   }
}