import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { Observable } from 'rxjs';
import { Device } from '../model/device';
import { LocalStorageService } from './local-storage.service';
import { ProvidesFindAll } from './provides-find-all';

@Injectable({
  providedIn: 'root'
})
export class DeviceService extends LocalStorageService<Device> implements ProvidesFindAll<Device> {

  constructor() {
    super();
  }

  findAll(): Observable<Device[]> {
    const items = this.getStorage('devices');
    return of(items);
  }

  save(device: Device): Observable<Device> {
    const items = this.getStorage('devices');
    device.id = String(items.length);
    items.push(device);
    this.setStorage('devices', items);

    return of(device);
  }
}
