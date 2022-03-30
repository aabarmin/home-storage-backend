import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DataRecord } from '../model/data-record';
import { Flat } from '../model/flat';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private readonly backendUrl;

  constructor(private http: HttpClient) {
    this.backendUrl = `${environment.backendBase}/records`;
  }

  public save(record: DataRecord): Observable<DataRecord> {
    if (record.id) {
      const url = `${this.backendUrl}/${record.id}`;
      return this.http.put<DataRecord>(url, record);
    }
    delete record.id;
    return this.http.post<DataRecord>(this.backendUrl, record);
  }

  public findRecordsForFlat(flat: Flat): Observable<DataRecord[]> {
    const url = `${this.backendUrl}?flatId=${flat.id}`;
    return this.http.get<DataRecord[]>(url);
  }

  public findRecordsForFlatAndYear(
    flat: Flat,
    year: number
  ): Observable<DataRecord[]> {
    const url = `${this.backendUrl}?flatId=${flat.id}&year=${year}`;
    return this.http.get<DataRecord[]>(url);
  }
}
