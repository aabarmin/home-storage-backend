import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Flat } from '../model/flat';
import { of } from "rxjs";
import { Subject } from 'rxjs';
import { LocalStorageService } from './local-storage.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlatService extends LocalStorageService<Flat> {  

  private flats: Subject<Flat[]> = new BehaviorSubject<Flat[]>([]);
  public readonly flats$ = this.flats.asObservable();

  constructor() {
    super();
    if (!this.getStorage('flats')) {
      const items = [{
        title: "Ufa",
        alias: "ufa"
      }];
      this.setStorage('flats', items);
    }
    const items = this.getStorage('flats');
    this.flats.next(items);
  }

  public save(flat: Flat): Observable<Flat> {
    const items = this.getStorage('flats');
    items.push(flat);
    this.setStorage('flats', items);

    this.flats.next(items);

    return of(flat);
  }
}
