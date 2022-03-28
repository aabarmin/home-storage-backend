import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { map, Observable } from 'rxjs';
import { DataRecord } from 'src/app/model/data-record';
import { Device } from 'src/app/model/device';
import { DeviceService } from 'src/app/service/device.service';
import { DeviceDialogData } from '../device-dialog/device-dialog-data';
import { DashboardDeviceDialogComponent } from '../device-dialog/device-dialog.component';

@Component({
  selector: 'app-dashboard-month-data',
  templateUrl: './month-data.component.html',
  styleUrls: ['./month-data.component.css'],
})
export class MonthDataComponent {
  @Input()
  records: DataRecord[] = [];

  constructor(private dialog: MatDialog, public deviceService: DeviceService) {}

  public getDevice(record: DataRecord): Observable<Device> {
    return this.deviceService.findByAlias(record.device);
  }

  public getDeviceReading(record: DataRecord): Observable<String> {
    return this.getDevice(record).pipe(
      map((device) => {
        if (!device.needReadings) return 'Not required';
        if (!record.reading) return 'Not provided';
        return String(record.reading);
      })
    );
  }

  onEditDataRecord(record: DataRecord): void {
    const dialogData: DeviceDialogData = {
      record,
    };
    this.dialog
      .open(DashboardDeviceDialogComponent, {
        data: dialogData,
      })
      .afterClosed()
      .subscribe(() => {});
  }
}
