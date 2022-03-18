import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Device } from 'src/app/model/device';
import { BackendDataSource } from 'src/app/service/backend-data-source';
import { DeviceService } from 'src/app/service/device.service';
import { DeviceDialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class DeviceListComponent implements OnInit {
  displayedColumns: string[] = [
    "title",
    "flat",
    "features",
    "alias"
  ];

  constructor(
    public deviceService: DeviceService,
    private dialog: MatDialog
  ) { } 

  ngOnInit(): void {
    
  }

  public onNewDevice(): void {
    this.dialog.open(DeviceDialogComponent).afterClosed().subscribe((result: Device) => {
      if (!result) {
        return;
      }
      this.deviceService.save(result).subscribe(() => {
        
      });
    })
  }
}
