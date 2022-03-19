import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DeviceDialogComponent } from 'src/app/devices/dialog/dialog.component';
import { DataRecord } from 'src/app/model/data-record';
import { DeviceDialogData } from '../device-dialog/device-dialog-data';
import { DashboardDeviceDialogComponent } from '../device-dialog/device-dialog.component';
import { DashboardDocumentDialogComponent } from '../document-dialog/document-dialog.component';
import { DashboardReadingDialogComponent } from '../reading-dialog/reading-dialog.component';

@Component({
  selector: 'app-dashboard-month-data',
  templateUrl: './month-data.component.html',
  styleUrls: ['./month-data.component.css']
})
export class MonthDataComponent implements OnInit {
  @Input()
  records: DataRecord[] = [];

  constructor(
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  onAddReadingClick(record: DataRecord): void {
    const dialogData: DeviceDialogData = {
      record
    };
    this.dialog.open(DashboardDeviceDialogComponent, {
      data: dialogData
    }).afterClosed().subscribe(() => {

    })
  }

  onAddDocumentClick(documentType: string): void {
    this.dialog.open(DashboardDocumentDialogComponent).afterClosed().subscribe(() => {
      
    });
  }

  public shouldDisplayReadings(record: DataRecord): boolean {
    return record.device.needReadings;
  }

  public readingsProvided(record: DataRecord): boolean {
    return !!record.reading;
  }
}
