import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";

@Component({
    selector: "mp-not-found",
    imports: [RouterLink],
    templateUrl: "./not-found.component.html",
    styleUrl: "./not-found.component.scss",
    host: { class: "center" }
})
export class NotFoundComponent {
}