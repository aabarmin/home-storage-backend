import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
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
  styleUrls: ['./device-dialog.component.css'],
})
export class DashboardDeviceDialogComponent implements OnInit {
  devices: Device[] = [];
  features: string[] = [];

  formGroup = new FormGroup({
    id: new FormControl(),
    date: new FormControl(new Date(), [Validators.required]),
    flat: new FormControl(null, [Validators.required]),
    device: new FormControl(null, [Validators.required]),
    reading: new FormControl('', [Validators.pattern('\\d+')]),
    invoiceFile: new FormControl(null),
    receiptFile: new FormControl(null),
  });

  flats: Flat[] = [];

  constructor(
    private flatService: FlatService,
    private deviceService: DeviceService,
    private dataService: DataService,
    private dialog: MatDialogRef<DashboardDeviceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: DeviceDialogData
  ) {
    if (this.dialogData?.record) {
      const record = this.dialogData.record;
      setTimeout(() => {
        this.formGroup.setValue(record);
      }, 1);
    }
  }

  ngOnInit(): void {
    this.formGroup.get('flat')?.valueChanges.subscribe((flatAlias) => {
      this.flatService.findByAlias(flatAlias).subscribe((flat) => {
        this.deviceService.findAllByFlat(flat).subscribe((devices) => {
          this.devices = devices;
        });
      });
    });

    this.formGroup.get('device')?.valueChanges.subscribe((deviceAlias) => {
      this.deviceService.findByAlias(deviceAlias).subscribe((device) => {
        this.features = this.extractFeatures(device);
      });
    });

    this.flatService.findAll().subscribe((flats) => {
      this.flats = flats;
    });
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
    this.dataService.save(dataRecord).subscribe((saved) => {
      this.dialog.close(saved);
    });
  }
}
