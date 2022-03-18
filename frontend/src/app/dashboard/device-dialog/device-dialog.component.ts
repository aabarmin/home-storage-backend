import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { zip } from 'rxjs';
import { of } from 'rxjs';
import { Observable } from 'rxjs';
import { DataRecord } from 'src/app/model/data-record';
import { Device } from 'src/app/model/device';
import { FileId } from 'src/app/model/file-id';
import { Flat } from 'src/app/model/flat';
import { DataService } from 'src/app/service/data.service';
import { DeviceService } from 'src/app/service/device.service';
import { FileService } from 'src/app/service/file.service';
import { FlatService } from 'src/app/service/flat.service';

@Component({
  selector: 'app-dashboard-device-dialog',
  templateUrl: './device-dialog.component.html',
  styleUrls: ['./device-dialog.component.css']
})
export class DashboardDeviceDialogComponent {
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
    private fileService: FileService, 
    private dataService: DataService, 
    private dialog: MatDialogRef<DashboardDeviceDialogComponent>
  ) {     
    const flatChange$ = this.formGroup.get('flat')?.valueChanges as Observable<Flat>;
    this.devices$ = this.deviceService.findAllByFlat(flatChange$);

    const deviceChange$ = this.formGroup.get('device')?.valueChanges as Observable<Device>;
    this.features$ = deviceChange$.pipe(
      map(device => this.extractFeatures(device))
    );
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
    zip(this.uploadBill(form), this.uploadInvoice(form)).subscribe(([billId, invoiceId]) => {
      dataRecord.receiptFile = billId;
      dataRecord.invoiceFile = invoiceId;

      this.dataService.save(dataRecord).subscribe((saved) => {
        this.dialog.close(saved);
      })
    })
  }

  private uploadBill(form: FormGroup): Observable<FileId | undefined> {
    return this.uploadFile(form, 'receiptFile');
  }

  private uploadInvoice(form: FormGroup): Observable<FileId | undefined> {
    return this.uploadFile(form, 'invoiceFile');
  }

  private uploadFile(form: FormGroup, field: string): Observable<FileId | undefined> {
    const value = form.get(field)?.value;
    if (!value) {
      return of(undefined);
    }
    return this.fileService.upload(value);
  }
}
