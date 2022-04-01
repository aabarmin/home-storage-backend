import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { Flat } from 'src/app/model/flat';
import { FlatService } from 'src/app/service/flat.service';
import { FlatDialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-flats-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class FlatsListComponent implements OnInit {
  @ViewChild(MatTable)
  flatsTable!: MatTable<Flat>;

  displayedColumns: String[] = ['title', 'alias', 'actions'];

  records: Flat[] = [];

  constructor(public flatService: FlatService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.onRefresh();
  }

  onRefresh(): void {
    this.flatService.findAll().subscribe((flats) => {
      this.records = flats;
    });
  }

  public onNewFlat(): void {
    this.dialog
      .open(FlatDialogComponent)
      .afterClosed()
      .subscribe((result: Flat) => {
        if (!result) {
          return;
        }
        this.flatService.save(result).subscribe(() => {
          this.onRefresh();
        });
      });
  }
}
