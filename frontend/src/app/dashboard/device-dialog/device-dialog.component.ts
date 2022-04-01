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
    id: new FormControl(undefined),
    date: new FormControl(new Date(), [Validators.required]),
    flatId: new FormControl(null, [Validators.required]),
    deviceId: new FormControl(null, [Validators.required]),
    reading: new FormControl('', [Validators.pattern('\\d+')]),
    invoiceFileId: new FormControl(null),
    receiptFileId: new FormControl(null),
  });

  flats: Flat[] = [];

  constructor(
    private flatService: FlatService,
    private deviceService: DeviceService,
    private dataService: DataService,
    private dialog: MatDialogRef<DashboardDeviceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: DeviceDialogData
  ) {}

  ngOnInit(): void {
    this.formGroup.get('flatId')?.valueChanges.subscribe((flatAlias) => {
      this.flatService.findById(flatAlias).subscribe((flat) => {
        this.deviceService.findAllByFlat(flat).subscribe((devices) => {
          this.devices = devices;
        });
      });
    });

    this.formGroup.get('deviceId')?.valueChanges.subscribe((deviceAlias) => {
      this.deviceService.findById(deviceAlias).subscribe((device) => {
        this.features = this.extractFeatures(device);
      });
    });

    this.flatService.findAll().subscribe((flats) => {
      this.flats = flats;
    });

    if (this.dialogData?.record) {
      const record = this.dialogData.record;
      this.formGroup.patchValue(record);
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
    this.dataService.save(dataRecord).subscribe((saved) => {
      this.dialog.close(saved);
    });
  }
}
