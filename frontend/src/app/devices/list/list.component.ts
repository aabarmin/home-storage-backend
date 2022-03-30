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
  private cache: { [key: number]: Flat } = {};

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
      const flatIds = [...new Set(devices.map((device) => device.flatId))];
      const observables = flatIds.map((flatId) =>
        this.flatService.findById(flatId)
      );
      forkJoin(observables).subscribe((flats) => {
        const cache: { [key: number]: Flat } = {};
        flats.forEach((flat) => (cache[flat.id] = flat));

        this.records = devices.map((device) => {
          return new Row(device, cache[device.flatId]);
        });
      });
    });
  }

  public getFlatName(device: Device): Observable<string> {
    if (device.flatId in this.cache) {
      const flat = this.cache[device.flatId];
      return of(flat.title);
    }
    return this.flatService.findById(device.flatId).pipe(
      tap((flat) => (this.cache[flat.id] = flat)),
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
        this.deviceService.save(result).subscribe(() => {
          this.onRefresh();
        });
      });
  }
}
