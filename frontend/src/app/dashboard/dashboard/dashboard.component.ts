import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { combineLatest, forkJoin, map, Observable, of } from 'rxjs';
import { DataRecord } from 'src/app/model/data-record';
import { FileId } from 'src/app/model/file-id';
import { Flat } from 'src/app/model/flat';
import { DataService } from 'src/app/service/data.service';
import { DeviceService } from 'src/app/service/device.service';
import { FileService } from 'src/app/service/file.service';
import { FlatService } from 'src/app/service/flat.service';
import { DashboardDeviceDialogComponent } from '../device-dialog/device-dialog.component';
import { DashboardDataRecord, DashboardRecord } from './dashboard-record';

interface FormValue {
  flat?: string;
  year?: number;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  flats: Flat[] = [];
  years: number[] = [];
  dashboard: DashboardRecord[] = [];

  formGroup = this.fb.group({
    flat: this.fb.control(null),
    year: this.fb.control(null),
  });

  constructor(
    private dialog: MatDialog,
    private fb: FormBuilder,
    private flatService: FlatService,
    private fileService: FileService,
    private deviceService: DeviceService,
    private dataService: DataService
  ) {}

  ngOnInit(): void {
    this.flatService.findAll().subscribe((flats) => {
      this.flats = flats;
      if (flats.length == 0) return;
      this.formGroup.patchValue({
        flat: flats[0].alias,
      });
    });

    this.formGroup.get('flat')?.valueChanges.subscribe((flatAlias) => {
      this.flatService.findByAlias(flatAlias).subscribe((flat) => {
        this.dataService.findRecordsForFlat(flat).subscribe((records) => {
          const values = records.map((r) => this.getRecordYear(r));
          const years = [...new Set(values)];
          this.years = years;

          // setting default value
          if (years.length == 0) return;
          this.formGroup.patchValue({
            year: years[0],
          });
        });
      });
    });

    // when flat and year are selected, get records
    combineLatest([
      this.formGroup.get('flat')?.valueChanges as Observable<string>,
      this.formGroup.get('year')?.valueChanges as Observable<number>,
    ]).subscribe(([flatAlias, year]) => {
      this.refresh(flatAlias, year);
    });
  }

  private refresh(flatAlias: string, year: number): void {
    this.flatService.findByAlias(flatAlias).subscribe((flat) => {
      this.dashboard = [];
      this.dataService
        .findRecordsForFlatAndYear(flat, year)
        .subscribe((records) => {
          this.toDashboard(records).subscribe((dashboardRecords) => {
            this.dashboard = dashboardRecords;
          });
        });
    });
  }

  public onRefresh(): void {
    const formValue = this.formGroup.value as FormValue;
    if (formValue.flat && formValue.year) {
      this.refresh(formValue.flat, formValue.year);
    }
  }

  private toDashboard(records: DataRecord[]): Observable<DashboardRecord[]> {
    return forkJoin(records.map((r) => this.toDashboardRecord(r))).pipe(
      map((dashboardRecords) => {
        const result: { [key: string]: DashboardRecord } = {};

        dashboardRecords.forEach((record) => {
          const month = this.getRecordMonth(record);
          let section = result[month];
          if (!section) {
            section = new DashboardRecord(month);
            result[month] = section;
          }
          section.addRecord(record);
        });

        return Object.values(result);
      })
    );
  }

  private toDashboardRecord(
    record: DataRecord
  ): Observable<DashboardDataRecord> {
    return forkJoin([
      of(record),
      this.flatService.findByAlias(record.flat),
      this.deviceService.findByAlias(record.device),
      this.getFileInfo(record.invoiceFile),
      this.getFileInfo(record.receiptFile),
    ]).pipe(
      map(([record, flat, device, invoiceFile, receiptFile]) => {
        return new DashboardDataRecord(
          record,
          device,
          flat,
          invoiceFile,
          receiptFile
        );
      })
    );
  }

  private getFileInfo(
    fileId: string | null | undefined
  ): Observable<FileId | null> {
    if (!fileId) return of(null);
    return this.fileService.findFileId(fileId);
  }

  private getRecordDate(record: DataRecord): Date {
    let recordDate = record.date;
    if (typeof recordDate == 'string') {
      recordDate = new Date(String(recordDate));
    }
    return recordDate;
  }

  private getRecordYear(record: DataRecord): number {
    const recordDate = this.getRecordDate(record);
    return recordDate.getFullYear();
  }

  private getRecordMonth(record: DataRecord | DashboardDataRecord): string {
    if (record instanceof DashboardDataRecord) record = record.record;
    const recordDate = this.getRecordDate(record);
    const monthNames = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December',
    ];
    return monthNames[recordDate.getMonth()];
  }

  onAddDeviceDataClick(): void {
    this.dialog
      .open(DashboardDeviceDialogComponent)
      .afterClosed()
      .subscribe((result) => {
        this.onRefresh();
      });
  }
}
