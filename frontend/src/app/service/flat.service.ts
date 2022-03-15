import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Flat } from '../model/flat';
import { of, from } from "rxjs";
import { Subject } from 'rxjs';
import { ProvidesFindAll } from './provides-find-all';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class FlatService extends LocalStorageService<Flat> implements ProvidesFindAll<Flat> {  

  constructor() {
    super();
    if (!localStorage.getItem('flats')) {
      this.setStorage('flats', [{
        id: "12345-12341234",
        title: "Ufa",
        alias: "ufa"
      }]);
    }
  }

  findAll(): Observable<Flat[]> {
    const items = this.getStorage('flats');
    return of(items);
  }

  public save(flat: Flat): Observable<Flat> {
    const items = this.getStorage('flats');
    flat.id = String(items.length);
    items.push(flat);
    this.setStorage('flats', items);

    return of(flat);
  }
}
