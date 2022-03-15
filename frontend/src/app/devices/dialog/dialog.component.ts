import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DeviceFormComponent } from '../form/form.component';

@Component({
  selector: 'app-devices-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DeviceDialogComponent implements OnInit {
  formValid: boolean = false;

  @ViewChild(DeviceFormComponent)
  formComponent?: DeviceFormComponent;

  constructor(
    private dialog: MatDialogRef<DeviceDialogComponent>
  ) { }

  ngOnInit(): void {
  }

  onFormValidStateChange(valid: boolean): void {
    this.formValid = valid;
  }

  onCancel(): void {
    this.dialog.close(null);
  }

  onSubmit(): void {
    this.dialog.close(this.formComponent?.getFormValue());
  }
}
