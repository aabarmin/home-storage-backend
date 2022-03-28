import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  combineLatest,
  Observable,
  of,
  Subject,
  tap,
} from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { DataRecord } from '../model/data-record';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class DataService extends LocalStorageService<DataRecord> {
  private records: Subject<DataRecord[]> = new BehaviorSubject<DataRecord[]>(
    []
  );
  public readonly records$ = this.records.asObservable();

  constructor() {
    super();
    const items = this.getStorage('records');
    this.records.next(items);
  }

  public save(record: DataRecord): Observable<DataRecord> {
    let items = this.getStorage('records');
    if (!record.id) {
      record.id = items.length + 1;
    } else {
      items = items.filter((item) => item.id != record.id);
    }
    items.push(record);

    this.setStorage('records', items);
    this.records.next(items);

    return of(record);
  }

  public findRecordsForFlat(
    flatAlias$: Observable<String>
  ): Observable<DataRecord[]> {
    return combineLatest([this.records$, flatAlias$]).pipe(
      tap(([records, alias]) =>
        console.log(`Records: ${records.length}, alias: ${alias}`)
      ),
      map(([records, flatAlias]) => {
        const filtered = records.filter((record) => record.flat == flatAlias);
        return filtered;
      })
    );
  }

  public findRecordsForFlatAndYear(
    flatAlias$: Observable<String>,
    year$: Observable<Number>
  ): Observable<DataRecord[]> {
    return combineLatest([this.records$, flatAlias$, year$]).pipe(
      map(([records, flatAlias, year]) => {
        const filtered = records
          .filter((item: DataRecord) => {
            return item.flat == flatAlias;
          })
          .filter((item) => {
            let date = item.date;
            if (typeof date == 'string') {
              date = new Date(String(date));
            }
            const itemYear = date.getFullYear();
            return itemYear == year;
          });
        return filtered;
      })
    );
  }
}
