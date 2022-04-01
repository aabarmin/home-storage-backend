import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Flat } from '../model/flat';

@Injectable({
  providedIn: 'root',
})
export class FlatService {
  private readonly backendUrl: string;

  constructor(private http: HttpClient) {
    this.backendUrl = `${environment.backendBase}/flats`;
  }

  public findAll(): Observable<Flat[]> {
    return this.http.get<Flat[]>(this.backendUrl);
  }

  public findById(id: number): Observable<Flat> {
    const url = `${this.backendUrl}/${id}`;
    return this.http.get<Flat>(url);
  }

  public save(flat: Flat): Observable<Flat> {
    return this.http.post<Flat>(this.backendUrl, flat);
  }
}
