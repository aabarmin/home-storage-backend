import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { mergeMap, Observable } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { environment } from 'src/environments/environment';
import { Device } from '../model/device';

@Injectable({
  providedIn: 'root',
})
export class DeviceService {
  private readonly backendUrl: string;

  constructor(private http: HttpClient) {
    this.backendUrl = `${environment.backendBase}/devices`;
  }

  /**
   * Find all devices.
   *
   * @returns all devices
   */
  findAll(): Observable<Device[]> {
    return this.http.get<Device[]>(this.backendUrl);
  }

  /**
   * Return a device with the given alias.
   *
   * @param alias$ to look for
   */
  findByAlias(alias: String): Observable<Device> {
    const url = `${this.backendUrl}/${alias}`;
    return this.http.get<Device>(url);
  }

  findAllByFlat(flat$: Observable<string>): Observable<Device[]> {
    return flat$.pipe(
      map((flat) => `${this.backendUrl}?flat=flat`),
      mergeMap((url) => this.http.get<Device[]>(url))
    );
  }

  save(device: Device): Observable<Device> {
    return this.http.post<Device>(this.backendUrl, device);
  }
}
