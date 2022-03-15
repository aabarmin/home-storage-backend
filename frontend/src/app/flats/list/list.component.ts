import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { Flat } from 'src/app/model/flat';
import { BackendDataSource } from 'src/app/service/backend-data-source';
import { FlatService } from 'src/app/service/flat.service';
import { FlatDialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-flats-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class FlatsListComponent implements OnInit {
  @ViewChild(MatTable) 
  flatsTable!: MatTable<Flat>;

  displayedColumns: String[] = [
    "title",
    "alias",
    "actions"
  ];
  dataSource: BackendDataSource<Flat> = new BackendDataSource(this.flatService);

  constructor(
    private flatService: FlatService, 
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.dataSource.refresh();
  }  

  public onNewFlat(): void {
    this.dialog.open(FlatDialogComponent).afterClosed().subscribe((result: Flat) => {
      if (!result) {
        return;
      }
      this.flatService.save(result).subscribe(() => {
        this.dataSource.refresh();
        this.flatsTable.renderRows();
      });
    })
  }

}
