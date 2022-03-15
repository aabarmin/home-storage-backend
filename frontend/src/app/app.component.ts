import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  sidebarOpened: boolean = false;

  public sidebarToggle(): void {
    this.sidebarOpened = !this.sidebarOpened;
  }
}
