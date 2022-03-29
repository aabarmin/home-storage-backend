import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Device } from '../model/device';
import { Flat } from '../model/flat';

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

  findAllByFlat(flat: Flat): Observable<Device[]> {
    const url = `${this.backendUrl}?flat=${flat.alias}`;
    return this.http.get<Device[]>(url);
  }

  save(device: Device): Observable<Device> {
    return this.http.post<Device>(this.backendUrl, device);
  }
}
