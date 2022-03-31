import { Output, EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { VersionInfo } from '../model/version-info';
import { VersionService } from '../service/version.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
})
export class ToolbarComponent implements OnInit {
  @Output()
  onSidebarToggle: EventEmitter<any> = new EventEmitter<any>();

  versionInfo: VersionInfo = {
    version: 'not set',
    build: 'not set',
    name: 'not set',
  };

  constructor(private versionService: VersionService) {}

  public sidebarToggleClick() {
    this.onSidebarToggle.emit();
  }

  ngOnInit(): void {
    this.versionService.getEnvironmentInfo().subscribe((version) => {
      this.versionInfo = version;
    });
  }
}
