import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Device } from 'src/app/model/device';
import { Flat } from 'src/app/model/flat';
import { FlatService } from 'src/app/service/flat.service';

@Component({
  selector: 'app-devices-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class DeviceFormComponent implements OnInit {
  @Output()
  onValidityChange: EventEmitter<boolean> = new EventEmitter();

  flats: Flat[] = [];

  formGroup: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required]),
    flatId: new FormControl('', [Validators.required]),
    alias: new FormControl('', [
      Validators.required,
      Validators.pattern('[a-z]+'),
    ]),
    needInvoices: new FormControl(true),
    needReceipts: new FormControl(true),
    needReadings: new FormControl(true),
  });

  constructor(public flatService: FlatService) {}

  ngOnInit(): void {
    this.formGroup.valueChanges.subscribe(() => {
      console.log(this.formGroup.value);
      this.onValidityChange.next(this.formGroup.valid);
    });

    this.flatService.findAll().subscribe((flats) => {
      this.flats = flats;
    });
  }

  public getFormValue(): Device {
    return this.formGroup.value as Device;
  }
}
