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
  dataSource: BackendDataSource<Device> = new BackendDataSource<Device>(this.service);
  displayedColumns: string[] = [
    "title",
    "flat",
    "features",
    "alias"
  ];

  constructor(
    private service: DeviceService,
    private dialog: MatDialog
  ) { } 

  ngOnInit(): void {
    this.dataSource.refresh();
  }

  public onNewDevice(): void {
    this.dialog.open(DeviceDialogComponent).afterClosed().subscribe((result: Device) => {
      if (!result) {
        return;
      }
      this.service.save(result).subscribe(() => {
        this.dataSource.refresh();
      });
    })
  }
}
