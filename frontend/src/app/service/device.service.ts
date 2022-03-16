import { Injectable } from '@angular/core';
import { mergeMap } from 'rxjs';
import { Observable } from 'rxjs';
import { of } from 'rxjs/internal/observable/of';
import { map } from 'rxjs/internal/operators/map';
import { Device } from '../model/device';
import { Flat } from '../model/flat';
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

  findAllByFlat(flat$: Observable<Flat>): Observable<Device[]> {
    return flat$.pipe(
      map(flat => {
        const items = this.getStorage('devices');
        const filtered = items.filter(device => device.flat.alias == flat.alias)
        return filtered;
      })
    );
  }

  save(device: Device): Observable<Device> {
    const items = this.getStorage('devices');
    items.push(device);
    this.setStorage('devices', items);

    return of(device);
  }
}
