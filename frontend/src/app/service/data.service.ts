import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { zip } from 'rxjs';
import { combineLatest } from 'rxjs';
import { mapTo } from 'rxjs';
import { Observable } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { DataRecord } from '../model/data-record';
import { Flat } from '../model/flat';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class DataService extends LocalStorageService<DataRecord> {
  public save(record: DataRecord): Observable<DataRecord> {
    const items = this.getStorage('records');
    items.push(record);
    this.setStorage('records', items);

    return of(record);
  }

  public findRecordsForFlat(flat$: Observable<Flat>): Observable<DataRecord[]> {
    return flat$.pipe(
      map(flat => {
        const items = this.getStorage('records');
        const filtered = items.filter(item => item.flat.alias == flat.alias);
        return filtered;
      })
    )
  }

  public findRecordsForFlatAndYear(
    flat$: Observable<Flat>, 
    year$: Observable<Number>
    ): Observable<DataRecord[]> {

    return combineLatest([flat$, year$]).pipe(
      map(([flat, year]) => {
        const items = this.getStorage('records');
        const filtered = items
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
