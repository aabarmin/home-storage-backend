import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FlatFormComponent } from '../form/form.component';

@Component({
  selector: 'app-flats-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class FlatDialogComponent implements OnInit {
  formValid: boolean = false;

  @ViewChild(FlatFormComponent)
  private flatForm!: FlatFormComponent;

  constructor(private dialogRef: MatDialogRef<FlatDialogComponent>) { }

  ngOnInit(): void {
  }

  public onFormValidStateChange(value: boolean) {
    this.formValid = value;
  }

  public onSubmit(): void {
    const value = this.flatForm.getFormValue();
    this.dialogRef.close(value);
  }

  public onCancel(): void {
    this.dialogRef.close(null);
  }
}
