import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { forkJoin, map, Observable, of, tap } from 'rxjs';
import { Device } from 'src/app/model/device';
import { Flat } from 'src/app/model/flat';
import { DeviceService } from 'src/app/service/device.service';
import { FlatService } from 'src/app/service/flat.service';
import { DeviceDialogComponent } from '../dialog/dialog.component';

class Row {
  constructor(public device: Device, public flat: Flat) {}
}

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class DeviceListComponent implements OnInit {
  displayedColumns: string[] = ['title', 'flat', 'features', 'alias'];
  private cache: { [key: string]: Flat } = {};

  records: Row[] = [];

  constructor(
    public deviceService: DeviceService,
    private flatService: FlatService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.onRefresh();
  }

  onRefresh(): void {
    this.deviceService.findAll().subscribe((devices) => {
      const flats = [...new Set(devices.map((device) => device.flat))];
      const observables = flats.map((alias) =>
        this.flatService.findByAlias(alias)
      );
      forkJoin(observables).subscribe((flats) => {
        const cache: { [key: string]: Flat } = {};
        flats.forEach((flat) => (cache[flat.alias] = flat));

        this.records = devices.map((device) => {
          return new Row(device, cache[device.flat]);
        });
      });
    });
  }

  public getFlatName(device: Device): Observable<string> {
    if (device.flat in this.cache) {
      const flat = this.cache[device.flat];
      return of(flat.title);
    }
    return this.flatService.findByAlias(device.flat).pipe(
      tap((flat) => (this.cache[flat.alias] = flat)),
      map((flat) => flat.title)
    );
  }

  public onNewDevice(): void {
    this.dialog
      .open(DeviceDialogComponent)
      .afterClosed()
      .subscribe((result: Device) => {
        if (!result) {
          return;
        }
        this.deviceService.save(result).subscribe(() => {});
      });
  }
}
