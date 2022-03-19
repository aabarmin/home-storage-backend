import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { combineLatest } from 'rxjs';
import { Subject } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { Observable } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { DataRecord } from '../model/data-record';
import { Flat } from '../model/flat';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class DataService extends LocalStorageService<DataRecord> {
  private records: Subject<DataRecord[]> = new BehaviorSubject<DataRecord[]>([]);
  public readonly records$ = this.records.asObservable();

  constructor() {
    super();
    const items = this.getStorage('records');
    this.records.next(items);
  }

  public save(record: DataRecord): Observable<DataRecord> {
    const items = this.getStorage('records');
    items.push(record);
    this.setStorage('records', items);

    this.records.next(items);

    return of(record);
  }

  public findRecordsForFlat(flat$: Observable<Flat>): Observable<DataRecord[]> {
    return combineLatest([this.records$, flat$])
      .pipe(
        map(([records, flat]) => {
          const filtered = records.filter(record => record.flat.alias == flat.alias)
          return filtered;
        })
      );
  }

  public findRecordsForFlatAndYear(
    flat$: Observable<Flat>, 
    year$: Observable<Number>
    ): Observable<DataRecord[]> {

    return combineLatest([this.records$, flat$, year$]).pipe(
      map(([records, flat, year]) => {
        const filtered = records
          .filter(item => {
            return item.flat.alias = flat.alias
          })
          .filter(item => {
            let date = item.date;
            if (typeof(date) == 'string') {
              date = new Date(String(date))
            }
            const itemYear = date.getFullYear();
            return itemYear == year;
          })
        return filtered;
      })
    )
  }
}
