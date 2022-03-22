import { Injectable } from '@angular/core';
import { filter } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { Subject } from 'rxjs';
import { Observable } from 'rxjs';
import { of } from 'rxjs/internal/observable/of';
import { map } from 'rxjs/internal/operators/map';
import { Device } from '../model/device';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class DeviceService extends LocalStorageService<Device> {
  private devices: Subject<Device[]> = new BehaviorSubject<Device[]>([]);
  public readonly devices$: Observable<Device[]> = this.devices.asObservable();

  constructor() {
    super();

    const items = this.getStorage('devices');
    this.devices.next(items);
  }

  /**
   * Return a device with the given alias. 
   * 
   * @param alias$ to look for
   */
  findByAlias(alias$: Observable<string>): Observable<Device> {
    return alias$.pipe(
      map(alias => {
        const items = this.getStorage('devices');
        const filtered = items.filter(device => device.alias == alias);
        return filtered[0];
      })
    );
  }

  findAllByFlat(flat$: Observable<string>): Observable<Device[]> {
    return flat$.pipe(
      filter(flat => flat != null), 
      map(flat => {
        const items = this.getStorage('devices');
        const filtered = items.filter(device => device.flat.alias == flat)
        return filtered;
      })
    );
  }

  save(device: Device): Observable<Device> {
    const items = this.getStorage('devices');
    items.push(device);
    this.setStorage('devices', items);

    this.devices.next(items);

    return of(device);
  }
}
