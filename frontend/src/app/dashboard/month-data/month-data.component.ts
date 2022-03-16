import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DataRecord } from 'src/app/model/data-record';
import { DashboardDocumentDialogComponent } from '../document-dialog/document-dialog.component';
import { DashboardReadingDialogComponent } from '../reading-dialog/reading-dialog.component';

@Component({
  selector: 'app-dashboard-month-data',
  templateUrl: './month-data.component.html',
  styleUrls: ['./month-data.component.css']
})
export class MonthDataComponent implements OnInit {
  @Input()
  records: DataRecord[] = [];

  constructor(
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  onAddReadingClick(): void {
    this.dialog.open(DashboardReadingDialogComponent).afterClosed().subscribe(() => {

    })
  }

  onAddDocumentClick(documentType: string): void {
    this.dialog.open(DashboardDocumentDialogComponent).afterClosed().subscribe(() => {
      
    });
  }
}
