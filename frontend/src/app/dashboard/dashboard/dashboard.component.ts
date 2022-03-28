import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { map, Observable, Subject, tap } from 'rxjs';
import { DataRecord } from 'src/app/model/data-record';
import { Flat } from 'src/app/model/flat';
import { DataService } from 'src/app/service/data.service';
import { FlatService } from 'src/app/service/flat.service';
import { DashboardDeviceDialogComponent } from '../device-dialog/device-dialog.component';
import { DashboardRecord } from './dashboard-record';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  flats$: Observable<Flat[]>;
  years$: Observable<Number[]>;
  dashboard$: Observable<DashboardRecord[]>;

  formGroup = this.fb.group({
    flat: this.fb.control(null),
    year: this.fb.control(null)
  })

  constructor(
    private dialog: MatDialog,
    private fb: FormBuilder,
    public flatService: FlatService,
    private dataService: DataService
  ) {

    const flatChange$ = this.formGroup.get('flat')?.valueChanges as Subject<String>;
    this.flats$ = this.flatService.flats$;
    this.years$ = this.dataService.findRecordsForFlat(flatChange$)
      .pipe(
        tap(records => console.log(records.length, records)),
        map(records => records.map(r => this.getRecordYear(r))),
        map(records => [...new Set(records)])
      );

    flatChange$.subscribe(value => console.log("Change -> " + value));

    const yearChange$ = this.formGroup.get('year')?.valueChanges as Observable<Number>;
    this.dashboard$ = this.dataService.findRecordsForFlatAndYear(flatChange$, yearChange$)
      .pipe(
        map(records => this.toDashboard(records))
      );

    setTimeout(() => {
      this.years$.subscribe(years => {
        this.formGroup.patchValue({
          year: years[0]
        })
      })
      this.flatService.flats$.subscribe(flats => {
        this.formGroup.get('flat')?.setValue(flats[0].alias)
      })
    }, 0)
  }

  ngOnInit(): void {

  }

  private toDashboard(records: DataRecord[]): DashboardRecord[] {
    const result: { [key: string]: DashboardRecord } = {};
    records.forEach(record => {
      const month = this.getRecordMonth(record);
      let section = result[month];
      if (!section) {
        section = new DashboardRecord(month);
        result[month] = section;
      }
      section.addRecord(record);
    });
    return Object.values(result);
  }

  private getRecordDate(record: DataRecord): Date {
    let recordDate = record.date;
    if (typeof (recordDate) == 'string') {
      recordDate = new Date(String(recordDate));
    }
    return recordDate;
  }

  private getRecordYear(record: DataRecord): number {
    const recordDate = this.getRecordDate(record);
    return recordDate.getFullYear();
  }

  private getRecordMonth(record: DataRecord): string {
    const recordDate = this.getRecordDate(record);
    const monthNames = ["January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"
    ];
    return monthNames[recordDate.getMonth()];
  }

  onAddDeviceDataClick(): void {
    this.dialog.open(DashboardDeviceDialogComponent).afterClosed().subscribe((result) => {
      // refresh data here
    })
  }
}
