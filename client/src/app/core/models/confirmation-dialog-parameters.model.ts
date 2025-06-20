export interface ConfirmationDialogParameters {
   title: string;
   content: string;
   cancelButtonText: string;
   confirmButtonText: string;
   confirmAction?: () => Promise<unknown>;
}
