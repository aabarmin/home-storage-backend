import { OnInit } from '@angular/core';
import { Inject } from '@angular/core';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { map } from 'rxjs';
import { Observable } from 'rxjs';
import { DataRecord } from 'src/app/model/data-record';
import { Device } from 'src/app/model/device';
import { Flat } from 'src/app/model/flat';
import { DataService } from 'src/app/service/data.service';
import { DeviceService } from 'src/app/service/device.service';
import { FlatService } from 'src/app/service/flat.service';
import { DeviceDialogData } from './device-dialog-data';

@Component({
  selector: 'app-dashboard-device-dialog',
  templateUrl: './device-dialog.component.html',
  styleUrls: ['./device-dialog.component.css']
})
export class DashboardDeviceDialogComponent implements OnInit {
  devices$: Observable<Device[]>;
  features$: Observable<string[]> = new Observable<string[]>();

  formGroup = new FormGroup({
    date: new FormControl(new Date(), [Validators.required]),
    flat: new FormControl(null, [Validators.required]),
    device: new FormControl(null, [Validators.required]),
    reading: new FormControl('', [Validators.pattern('\\d+')]),
    invoiceFile: new FormControl(null),
    receiptFile: new FormControl(null)
  });

  constructor(
    public flatService: FlatService,
    private deviceService: DeviceService,
    private dataService: DataService, 
    private dialog: MatDialogRef<DashboardDeviceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: DeviceDialogData
  ) {     
    const flatChange$ = this.formGroup.get('flat')?.valueChanges as Observable<Flat>;
    this.devices$ = this.deviceService.findAllByFlat(flatChange$);

    const deviceChange$ = this.formGroup.get('device')?.valueChanges as Observable<Device>;
    this.features$ = deviceChange$.pipe(
      map(device => this.extractFeatures(device))
    );
  }

  ngOnInit(): void {
    if (this.dialogData?.record) {
      const record = this.dialogData.record;
      this.formGroup.patchValue({
        date: record.date,
        flat: record.flat
      })
    }
  }

  private extractFeatures(device: Device): string[] {
    const result: string[] = [];
    if (device.needReceipts) {
      result.push('receipts');
    }
    if (device.needInvoices) {
      result.push('invoices');
    }
    if (device.needReadings) {
      result.push('readings');
    }
    return result;
  }

  onCancel(): void {
    this.dialog.close(null);
  }

  onSubmit(form: FormGroup) {
    const dataRecord: DataRecord = form.value as DataRecord;
    this.dataService.save(dataRecord).subscribe(saved => {
      this.dialog.close(saved);
    })
  }
}
