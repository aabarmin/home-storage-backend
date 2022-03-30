import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DashboardDataRecord } from '../dashboard/dashboard-record';
import { DeviceDialogData } from '../device-dialog/device-dialog-data';
import { DashboardDeviceDialogComponent } from '../device-dialog/device-dialog.component';

@Component({
  selector: 'app-dashboard-month-data',
  templateUrl: './month-data.component.html',
  styleUrls: ['./month-data.component.css'],
})
export class MonthDataComponent {
  @Input()
  records: DashboardDataRecord[] = [];

  @Output()
  onRecordUpdated: EventEmitter<any> = new EventEmitter<any>();

  constructor(private dialog: MatDialog) {}

  public getDeviceReading(record: DashboardDataRecord): string {
    const device = record.device;
    if (!device.needReadings) return 'Not required';
    if (!record.record.reading) return 'Not provided';
    return String(record.record.reading);
  }

  onEditDataRecord(record: DashboardDataRecord): void {
    const dialogData: DeviceDialogData = {
      record: record.record,
    };
    this.dialog
      .open(DashboardDeviceDialogComponent, {
        data: dialogData,
      })
      .afterClosed()
      .subscribe(() => {
        this.onRecordUpdated.emit();
      });
  }
}
