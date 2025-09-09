import { Component, Inject } from "@angular/core";
import { DailyRation } from "../../../../core/models/daily-ration.model";
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { CommonModule } from "@angular/common";
import { MatChipsModule } from "@angular/material/chips";
import { MatTableModule } from "@angular/material/table";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { MatButton } from "@angular/material/button";

@Component({
   selector: "mp-ration-reading-dialog-component",
   imports: [MatDialogModule,
      CommonModule,
      MatChipsModule,
      MatTableModule,
      MatButton
   ],
   templateUrl: "./ration-reading-dialog-component.component.html",
   styleUrl: "./ration-reading-dialog-component.component.scss"
})
export class RationReadingDialogComponentComponent {

   readonly ration: DailyRation;
   displayedColumns: string[] = ["ingredient", "quantity"];

   constructor(
      private dialogRef: MatDialogRef<RationReadingDialogComponentComponent>,
      @Inject(MAT_DIALOG_DATA) data: DailyRation,
      private readonly sanitizer: DomSanitizer
   ) {
      this.ration = { ...data }
   }

   isValidUrl(url: string | null | undefined): boolean {
      if (!url) return false;
      try {
         const parsed = new URL(url);
         return parsed.protocol === 'http:' || parsed.protocol === 'https:';
      } catch {
         return false;
      }
   }

   getSafeUrl(url: string): SafeUrl {
      return this.sanitizer.bypassSecurityTrustUrl(url);
   }
}
