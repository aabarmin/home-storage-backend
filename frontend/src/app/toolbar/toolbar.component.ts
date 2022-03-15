import { Output, EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent {
  @Output()
  onSidebarToggle: EventEmitter<any> = new EventEmitter<any>();

  public sidebarToggleClick() {
    this.onSidebarToggle.emit();
  }
}
