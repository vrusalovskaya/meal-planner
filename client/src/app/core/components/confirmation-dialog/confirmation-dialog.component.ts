import { Component, Inject } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import {
   MAT_DIALOG_DATA,
   MatDialogActions,
   MatDialogContent,
   MatDialogRef,
   MatDialogTitle,
} from "@angular/material/dialog";

import { ConfirmationDialogParameters } from "../../models/confirmation-dialog-parameters.model";
import { LoadingOverlayService } from "../../loading-overlay/loading-overlay.service";

@Component({
   selector: "mp-confirmation-dialog",
   imports: [
      MatDialogTitle,
      MatDialogContent,
      MatDialogActions,
      MatButtonModule,
      MatProgressSpinnerModule,
   ],
   templateUrl: "./confirmation-dialog.component.html",
   styleUrl: "./confirmation-dialog.component.scss"
})
export class ConfirmationDialogComponent {

   constructor(
      private readonly dialogRef: MatDialogRef<ConfirmationDialogComponent>,
      @Inject(MAT_DIALOG_DATA) readonly paratemers: ConfirmationDialogParameters,
      private readonly loadingOverlayService: LoadingOverlayService
   ) {
      this.dialogRef.backdropClick().subscribe(() => this.onCancel());
      this.dialogRef.keydownEvents().subscribe(event => {
         if (event.key === "Escape") {
            this.onCancel();
         }
      });
   }

   onCancel(): void {
      this.dialogRef.close(false);
   }

   async onConfirm(): Promise<void> {
      this.loadingOverlayService.show();
      this.dialogRef.disableClose = true;

      try {
         if (this.paratemers.confirmAction) {
            await this.paratemers.confirmAction();
         }
         this.dialogRef.close(true);
      } finally {
         this.loadingOverlayService.hide();
         this.dialogRef.disableClose = false;
      }
   }
}
